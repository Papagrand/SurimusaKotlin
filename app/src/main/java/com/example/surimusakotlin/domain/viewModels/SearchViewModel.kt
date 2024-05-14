package com.example.surimusakotlin.domain.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.domain.model.Common
import com.example.surimusakotlin.domain.model.Food
import com.example.surimusakotlin.domain.usecase.search.GetFoodInstantResponceByQueryUseCase
import com.example.surimusakotlin.domain.usecase.search.GetNutritionsForCommonListUseCase
import com.example.surimusakotlin.presentation.search.SearchHistoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getNutritionsForCommonListUseCase: GetNutritionsForCommonListUseCase,
    private val getFoodInstantResponceByQueryUseCase: GetFoodInstantResponceByQueryUseCase,
    val searchHistoryManager: SearchHistoryManager,
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _isNoData: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNoData = _isNoData.asStateFlow()

    private val _foodInstantList: MutableStateFlow<List<Food>> = MutableStateFlow(emptyList())
    val foodInstantList = _foodInstantList.asStateFlow()

    private fun prepareViewForRequestResult() {
        _foodInstantList.update { emptyList() }
        _isError.update { false }
        _isNoData.update { false }
        _isLoading.update { true }
    }


    fun makeFoodInstantRequestByQuery(query: String) {
        prepareViewForRequestResult()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var foodCommonList: List<Common> = emptyList()
                val foodInstant = getFoodInstantResponceByQueryUseCase.invoke(query)
                foodInstant.onSuccess { foodInstantResult ->
                    foodCommonList = foodInstantResult?.common ?: emptyList()
                    if (foodInstantResult == null || foodInstantResult.common.isEmpty()) {
                        _isNoData.update { true }
                        _isLoading.update { false }
                        return@launch
                    }
                }.onFailure {
                    _isError.update { true }
                }

                searchHistoryManager.addSearchQuery(query)

                val resultFoodCommonList = getNutritionsForCommonListUseCase(foodCommonList)
                resultFoodCommonList.onSuccess { foodList ->
                    _foodInstantList.update { foodList }
                }.onFailure { error ->
                    Log.e("Exception on", error.stackTraceToString())
                    _isError.update { true }
                }
            } catch (e: Exception) {
                Log.e("Exception on", e.stackTraceToString())
                _isError.update { true }
                _isLoading.update { false }
            }
            _isLoading.update { false }
        }
    }
}