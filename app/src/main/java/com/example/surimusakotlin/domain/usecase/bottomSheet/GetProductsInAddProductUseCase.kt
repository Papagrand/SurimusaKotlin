package com.example.surimusakotlin.domain.usecase.bottomSheet

import androidx.lifecycle.LiveData
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.data.repository.ProductRepository

class GetProductsInAddProductUseCase(private val repository: ProductRepository) {

    fun execute(id: Long): LiveData<List<Product>>  {
        return repository.getProductsByMealId(id)
    }

}