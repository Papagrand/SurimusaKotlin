package com.example.surimusakotlin.domain.repository

import com.example.surimusakotlin.data.api.RetrofitFoodInstance
import com.example.surimusakotlin.domain.model.FoodInstant
import com.example.surimusakotlin.domain.model.Nutrition
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response

interface FoodRepositoryInterface {

    suspend fun getFood(query: String): Response<FoodInstant>

    suspend fun getNutritions(query: String): Response<Nutrition>

}