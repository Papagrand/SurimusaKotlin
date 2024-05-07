package com.example.surimusakotlin.data.api

import com.example.surimusakotlin.model.Food
import com.example.surimusakotlin.model.FoodInstant
import com.example.surimusakotlin.model.Nutrition
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiFoodService {

//    @Headers(
//        "Content-Type: application/x-www-form-urlencoded",
//        "x-app-id: f55b564e",
//        "x-app-key: ebb09c5dc3cbef9a264656d6332c1935"
//    )
//    @GET("v2/search/item")
//    suspend fun getAdditionalFoodInfo(
//        @Query("nix_item_id") nixItemId: String
//    ): Response<Food>

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