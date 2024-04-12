package com.example.surimusakotlin.data.repository

import com.example.surimusakotlin.data.api.RetrofitFoodInstance
import com.example.surimusakotlin.model.Food
import com.example.surimusakotlin.model.FoodInformation
import com.example.surimusakotlin.model.FoodInstant
import retrofit2.Response

class FoodRepository {
    suspend fun getNutritions(query: String): Response<FoodInstant>{
        return RetrofitFoodInstance.api.searchFood(query)
    }

    suspend fun getAdditionalFoodInfo(nixItemId: String): Response<Food> {
        return RetrofitFoodInstance.api.getAdditionalFoodInfo(nixItemId)
    }
}