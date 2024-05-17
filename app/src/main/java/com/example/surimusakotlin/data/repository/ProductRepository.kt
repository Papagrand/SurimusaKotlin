package com.example.surimusakotlin.data.repository

import androidx.lifecycle.LiveData
import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.Entities.Product

class ProductRepository(private val totalNutritionDao: TotalNutritionDao) {

//    fun getProductByMealId(mealId: Long): List<Product>? {
//        return totalNutritionDao.getProductByMealId(mealId)
//    }
    fun insertProduct(product: Product){
        totalNutritionDao.insertProduct(product)
    }
    fun getProductsByMealId(id: Long): LiveData<List<Product>> {
        return totalNutritionDao.getProductsByMealId(id)
    }
    suspend fun deleteProductById(id: Long) {
        totalNutritionDao.deleteProductById(id)
    }

}