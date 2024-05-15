package com.example.surimusakotlin.data.repository

import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Product

class ProductRepository(private val totalNutritionDao: TotalNutritionDao) {

//    fun getProductByMealId(mealId: Long): List<Product>? {
//        return totalNutritionDao.getProductByMealId(mealId)
//    }
    fun insertProduct(product: Product){
        totalNutritionDao.insertProduct(product)
    }

}