package com.example.surimusakotlin.data.api

import com.example.surimusakotlin.domain.model.Food
import com.example.surimusakotlin.domain.model.FoodInstant
import com.example.surimusakotlin.domain.model.Nutrition
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiFoodService {

    @Headers(
        "Content-Type: application/json",
        "x-app-id: a090e737",
        "x-app-key: b10d8a3b5b06cc6f9d2997f0590c39d3"
    )
    @POST("v2/natural/nutrients")
    suspend fun fetchNutrients(@Body body: RequestBody):
            Response<Nutrition>

    @Headers(
        "Content-Type: application/json",
        "x-app-id: a090e737",
        "x-app-key: b10d8a3b5b06cc6f9d2997f0590c39d3"
    )
    @GET("v2/search/instant/")
    suspend fun searchFood(
        @Query("query") query: String
    ): Response<FoodInstant>


}