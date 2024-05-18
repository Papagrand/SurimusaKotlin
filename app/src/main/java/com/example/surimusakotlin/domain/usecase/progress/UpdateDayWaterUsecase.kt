package com.example.surimusakotlin.domain.usecase.progress

import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Total_nutritions

class UpdateDayWaterUsecase (private val totalNutritionsDao: TotalNutritionDao) {
    suspend fun updateDayWater(water: Double?, id: Long) {
        val totalNutritions = totalNutritionsDao.getTotalNutritionById(id) ?: Total_nutritions(id = id)

        totalNutritions.water = water

        totalNutritionsDao.updateTotalNutritions(totalNutritions)
    }
}