package com.example.surimusakotlin.domain.usecase.addingSearchedProduct

import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.data.repository.ProductRepository
import com.example.surimusakotlin.domain.FoodNutrientsManager

class AddingSearchedProductUseCase(private val productRepository: ProductRepository) {

    fun addProduct(foodNutrientsManager: FoodNutrientsManager, mealId: Long){

        val product = Product(
            productName = foodNutrientsManager.foodName,
            calories = foodNutrientsManager.calories,
            cholesterol = foodNutrientsManager.cholesterol,
            dietaryFiber = foodNutrientsManager.dietaryFiber,
            potassium = foodNutrientsManager.potassium,
            protein = foodNutrientsManager.protein,
            saturated_fat = foodNutrientsManager.saturated_fat,
            sodium = foodNutrientsManager.sodium,
            sugars = foodNutrientsManager.sugars,
            totalCarbohydrate = foodNutrientsManager.totalCarbohydrate,
            totalFat = foodNutrientsManager.totalFat,
            grams = foodNutrientsManager.grams,
            mealId = mealId

        )
        productRepository.insertProduct(product)
    }

}