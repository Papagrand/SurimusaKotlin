package com.example.surimusakotlin.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFoodInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://trackapi.nutritionix.com/")
            .addConverterFactory(GsonConverterFactory
            .create())
            .build()
    }
    val api: ApiFoodService by lazy {
        retrofit.create(ApiFoodService::class.java)
    }
}