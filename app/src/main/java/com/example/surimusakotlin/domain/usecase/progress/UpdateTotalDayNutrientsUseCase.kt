package com.example.surimusakotlin.domain.usecase.progress

import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Total_nutritions

class UpdateTotalDayNutrientsUseCase(private val totalNutritionsDao: TotalNutritionDao) {
    suspend fun updateTotalDayNutrients(calories: Double?, protein: Double?, carbohydrate: Double?, fat: Double?, id: Long) {
        val totalNutritions = totalNutritionsDao.getTotalNutritionById(id) ?: Total_nutritions(id = id)

        totalNutritions.totalDayCalories = calories
        totalNutritions.totalDayProtein =  protein
        totalNutritions.totalDayCarbohydrate = carbohydrate
        totalNutritions.totalDayFat = fat

        totalNutritionsDao.updateTotalNutritions(totalNutritions)
    }
}