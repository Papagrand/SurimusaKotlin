package com.example.surimusakotlin

import android.util.Log
import android.widget.Toast
import com.example.surimusakotlin.data.api.ApiFoodService
import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.model.Common
import com.example.surimusakotlin.model.Food
import com.example.surimusakotlin.model.FoodInstant
import com.example.surimusakotlin.model.Nutrition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val foodRepository: FoodRepository) {
    val foodInstantList: MutableStateFlow<List<Food>> = MutableStateFlow(mutableListOf())
    fun makeRequest(query: String){

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<FoodInstant> = foodRepository.getFood(query)
                if (response.isSuccessful) {
                    val food: FoodInstant? = response.body()
                    val foodList = food?.common ?: emptyList()

                    val tempMutableList = mutableListOf<Food>()

                    foodList.take(2).forEach{
                        val additionalInfoResponse = foodRepository.getNutritions(it.food_name)
                        if (additionalInfoResponse.isSuccessful){
                            val nutrition: Food? = additionalInfoResponse.body()?.foods?.get(0)
                            if (nutrition != null){
                                tempMutableList.add(nutrition)
                            }
                        }
                    }
                    foodInstantList.update { tempMutableList }
                } else {
                    foodInstantList.update { mutableListOf() }
                }
            } catch (e: Exception) {
                foodInstantList.update { mutableListOf() }
            }
        }

    }
}