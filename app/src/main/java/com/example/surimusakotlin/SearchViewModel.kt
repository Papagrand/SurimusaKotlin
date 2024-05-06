package com.example.surimusakotlin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.data.ScreenSwitchable
import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.model.Common
import com.example.surimusakotlin.model.Food
import com.example.surimusakotlin.model.FoodInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(
    private val foodRepository: FoodRepository,
    val searchHistoryManager: SearchHistoryManager,
) : ViewModel() {
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _foodInstantList: MutableStateFlow<List<Food>> = MutableStateFlow(emptyList())
    val foodInstantList = _foodInstantList.asStateFlow()

    private fun prepareViewForRequestResult() {
        _foodInstantList.update { emptyList() }
        _isError.update { false }
        _isLoading.update { true }
    }

    private suspend fun getFoodInstantResponseByQuery(query: String): FoodInstant? {
        val response: Response<FoodInstant> = foodRepository.getFood(query)

        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.e("Exception on", "111")
            null
        }
    }

    private suspend fun getNutritionsForCommonList(foodList: List<Common>): List<Food>? {
        val tempMutableList = mutableListOf<Food>()

        foodList.take(4).forEach {
            val additionalInfoResponse = foodRepository.getNutritions(it.food_name)

            if (additionalInfoResponse.isSuccessful) {

                val nutrition: Food? = additionalInfoResponse.body()?.foods?.get(0)
                if (nutrition != null) {
                    tempMutableList.add(nutrition)
                }
            } else {
                Log.e("КБЖУ not found for ", it.food_name)
                return null
            }
        }

        return tempMutableList
    }

    fun makeFoodInstantRequestByQuery(query: String) {
        prepareViewForRequestResult()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val foodInstant: FoodInstant? = getFoodInstantResponseByQuery(query)
                if (foodInstant == null) {
                    _isError.update { false }
                    return@launch
                }
                val foodCommonList = foodInstant.common

                searchHistoryManager.addSearchQuery(query)

                val foodList = getNutritionsForCommonList(foodCommonList)
                if (foodList == null) {
                    _isError.update { true }
                } else {
                    _foodInstantList.update { foodList }
                }
            } catch (e: Exception) {
                Log.e("Exception on", e.stackTraceToString())
                _isError.update { true }
            }
        }

        _isLoading.update { false }
    }

    fun deleteHistoryItemById(position: Int) {
        searchHistoryManager.deleteById(position)
    }
}