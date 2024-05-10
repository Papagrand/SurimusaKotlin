package com.example.surimusakotlin.domain.usecase.search

import com.example.surimusakotlin.domain.model.FoodInstant
import com.example.surimusakotlin.domain.repository.FoodRepositoryInterface
import retrofit2.Response
import retrofit2.http.Query

class GetFoodInstantResponceByQueryUseCase(private val foodRepositoryInterface: FoodRepositoryInterface) {

    suspend operator fun invoke(query: String): Result<FoodInstant?>{
        return try {
            val response: Response<FoodInstant> = foodRepositoryInterface.getFood(query)
            if (!response.isSuccessful){
             return Result.failure(Exception("Error fetching Food Instant data"))
            }
            Result.success(response.body())
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}