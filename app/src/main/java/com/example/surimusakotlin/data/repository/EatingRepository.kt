package com.example.surimusakotlin.data.repository

import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Eating

class EatingRepository(private val totalNutritionDao: TotalNutritionDao) {

    fun insertEating(eating: Eating) {
        val existingEating = eating.id?.let { totalNutritionDao.getEatingById(it) }
        if (existingEating != null) {
            totalNutritionDao.insertEating(eating)
        }
    }
    fun getAllEating(): List<Eating> {
        return totalNutritionDao.getEating()
    }

    fun deleteEating(eating: Eating){
        totalNutritionDao.deleteEating(eating)
    }
}