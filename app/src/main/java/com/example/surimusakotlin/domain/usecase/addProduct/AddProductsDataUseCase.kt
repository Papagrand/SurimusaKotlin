package com.example.surimusakotlin.domain.usecase.addProduct

import com.example.surimusakotlin.data.repository.EatingRepository
import com.example.surimusakotlin.data.repository.ProductRepository

class AddProductsDataUseCase(private val eatingRepository: EatingRepository) {
    fun addInformationOfProducts(mealId: Long){
        val products = eatingRepository.getProductsForEating(mealId)
        val eating = eatingRepository.getEatingById(mealId)

        eating?.let {
            it.totalCaloriesEating = products?.sumOf { product -> product.calories ?: 0.0 }
            it.totalFatsEating = products?.sumOf { product -> product.totalFat ?: 0.0 }
            it.totalCarbohydrateEating = products?.sumOf { product -> product.totalCarbohydrate ?: 0.0 }
            it.totalProteinsEating = products?.sumOf { product -> product.protein ?: 0.0 }
            it.nameProducts = products?.joinToString(separator = ", ") { product -> product.productName ?: "Unknown" }
            it.countProducts++;
            eatingRepository.updateEating(it)
        }
    }
}