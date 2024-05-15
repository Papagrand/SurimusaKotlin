package com.example.surimusakotlin.data.repository


import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Total_nutritions
class TotalNutritionRepository(private val totalNutritionDao: TotalNutritionDao) {
    fun getTotalNutritionById(id: Long): Total_nutritions? {
        return totalNutritionDao.getTotalNutritionById(id)
    }

    fun insertTotalNutrition(nutrition: Total_nutritions) {
        val existingTotalNutrition = nutrition.id?.let { totalNutritionDao.getTotalNutritionById(it) }
        if (existingTotalNutrition == null){
            totalNutritionDao.insertTotalNutritions(nutrition)
        }
    }


    fun getAllNutritions(): List<Total_nutritions> {
        return totalNutritionDao.getAllNutritions()
    }

    fun deleteTotalNutrition(nutrition: Total_nutritions){
        totalNutritionDao.deleteNutrition(nutrition)
    }
}