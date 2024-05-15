package com.example.surimusakotlin.data.repository

import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.Entities.Product

class EatingRepository(private val totalNutritionDao: TotalNutritionDao) {

    fun insertEating(eating: Eating) {
        val existingEating = eating.id?.let { totalNutritionDao.getEatingById(it) }
        if (existingEating == null) {
            totalNutritionDao.insertEating(eating)
        }
    }
    fun getEatingById(id: Long): Eating?{
        return totalNutritionDao.getEatingById(id)
    }
    fun getAllEating(): List<Eating> {
        return totalNutritionDao.getEating()
    }

    fun deleteEating(eating: Eating){
        totalNutritionDao.deleteEating(eating)
    }
    fun getProductsForEating(mealId: Long): List<Product>?{
        return totalNutritionDao.getProductByMealId(mealId)
    }
    fun updateEating(eating: Eating) {
        totalNutritionDao.updateEating(eating)
    }
}