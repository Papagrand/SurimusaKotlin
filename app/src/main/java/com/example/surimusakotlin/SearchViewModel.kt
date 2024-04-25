package com.example.surimusakotlin

import android.util.Log
import android.widget.Toast
import com.example.surimusakotlin.data.ScreenSwitchable
import com.example.surimusakotlin.data.api.ApiFoodService
import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.model.Common
import com.example.surimusakotlin.model.Food
import com.example.surimusakotlin.model.FoodInstant
import com.example.surimusakotlin.model.Nutrition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchViewModel(
    private val foodRepository: FoodRepository,
    private val screenSwitchable: ScreenSwitchable,
    private val searchHistoryManager: SearchHistoryManager,
) {
    var isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val foodInstantList: MutableStateFlow<List<Food>> = MutableStateFlow(mutableListOf())
    fun makeRequest(query: String) {
         CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.IO){
                    isLoading.update { true }
                }
                val response: Response<FoodInstant> = foodRepository.getFood(query)
                if (response.isSuccessful) {
                    val food: FoodInstant? = response.body()
                    val foodList = food?.common ?: emptyList()

                    val tempMutableList = mutableListOf<Food>()
                    if (foodList.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            screenSwitchable.hideError()
                            screenSwitchable.showNoData()
                        }
                        return@launch
                    }else{
                        withContext(Dispatchers.Main) {
                            screenSwitchable.hideError()
                            screenSwitchable.showData()
                        }
                    }
                    withContext(Dispatchers.Main){
                        searchHistoryManager.addSearchQuery(query)
                    }

                    foodList.take(4).forEach {
                        val additionalInfoResponse = foodRepository.getNutritions(it.food_name)
                        if (additionalInfoResponse.isSuccessful) {
                            val nutrition: Food? = additionalInfoResponse.body()?.foods?.get(0)
                            if (nutrition != null) {
                                tempMutableList.add(nutrition)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                screenSwitchable.showError()
                                Log.e("Exception on", it.food_name)
                            }
                        }
                    }
                    withContext(Dispatchers.Main){
                        isLoading.update { false }
                    }
                    foodInstantList.update { tempMutableList }
                } else {
                    foodInstantList.update { mutableListOf() }
                    withContext(Dispatchers.Main) {
                        isLoading.update { false }
                        screenSwitchable.showError()
                        Log.e("Exception on","111")
                    }
                }
            } catch (e: Exception) {
                isLoading.update { false }
                foodInstantList.update { mutableListOf() }
                withContext(Dispatchers.Main) {
                    Log.e("Exception on",e.stackTraceToString())
                    screenSwitchable.showError()
                }
            }
        }

    }

    fun deleteHistoryItemById(position: Int) {
        searchHistoryManager.deleteById(position)
    }
}