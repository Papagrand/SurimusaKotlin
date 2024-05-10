package com.example.surimusakotlin.domain.usecase.search

import com.example.surimusakotlin.domain.model.Common
import com.example.surimusakotlin.domain.model.Food
import com.example.surimusakotlin.domain.repository.FoodRepositoryInterface

class GetNutritionsForCommonListUseCase(private val foodRepositoryInterface: FoodRepositoryInterface) {

    suspend operator fun invoke(foodList: List<Common>): Result<List<Food>>{
        return try {
            val tempMutableList = mutableListOf<Food>()

            foodList.take(4).forEach {
                val additionalInfoResponse = foodRepositoryInterface.getNutritions(it.food_name)

                if (additionalInfoResponse.isSuccessful) {

                    val nutrition: Food? = additionalInfoResponse.body()?.foods?.get(0)
                    if (nutrition != null) {
                        tempMutableList.add(nutrition)
                    }
                } else {
                    return Result.failure(Exception("Error fetching nutritions data"))
                }
            }
            Result.success(tempMutableList)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

}