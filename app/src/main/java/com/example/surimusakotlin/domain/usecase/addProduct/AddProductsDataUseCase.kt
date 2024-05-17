package com.example.surimusakotlin.domain.usecase.addProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.repository.EatingRepository
import com.example.surimusakotlin.data.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProductsDataUseCase(private val eatingRepository: EatingRepository) {
    fun addInformationOfProducts(mealId: Long): LiveData<Eating?> {
        val result = MutableLiveData<Eating?>()

        // Launch a coroutine in the IO dispatcher for database operations
        CoroutineScope(Dispatchers.IO).launch {
            val eatingLiveData = eatingRepository.getEatingByIdLiveData(mealId)
            withContext(Dispatchers.Main) {
                eatingLiveData.observeForever { eating ->
                    eating?.let {
                        CoroutineScope(Dispatchers.IO).launch {
                            val products = eatingRepository.getProductsForEating(mealId)
                            withContext(Dispatchers.Default){
                                it.totalCaloriesEating = products?.sumOf { it.calories ?: 0.0 } ?: 0.0
                                it.totalFatsEating = products?.sumOf { it.totalFat ?: 0.0 } ?: 0.0
                                it.totalCarbohydrateEating = products?.sumOf { it.totalCarbohydrate ?: 0.0 } ?: 0.0
                                it.totalProteinsEating = products?.sumOf { it.protein ?: 0.0 } ?: 0.0
                                it.nameProducts = products?.joinToString(separator = ", ") { it.productName ?: "Unknown" } ?: "Unknown"
                                it.countProducts = products?.size ?: 0
                            }
                            eatingRepository.updateEating(it)

                            withContext(Dispatchers.Main) {
                                result.value = it
                            }
                        }
                    }
                }
            }
        }

        return result
    }
}
