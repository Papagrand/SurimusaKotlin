package com.example.surimusakotlin.data.repository

import com.example.surimusakotlin.data.api.RetrofitFoodInstance

import com.example.surimusakotlin.model.FoodInstant
import com.example.surimusakotlin.model.Nutrition
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response

class FoodRepository {
    suspend fun getFood(query: String): Response<FoodInstant>{
        return RetrofitFoodInstance.api.searchFood(query)
    }

    suspend fun getNutritions(query: String): Response<Nutrition> {
        val jsonObject = JSONObject()
        jsonObject.put("query", query)
        val requestBody = jsonObject
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        val response = RetrofitFoodInstance.api.fetchNutrients(requestBody)

        return response
    }

}