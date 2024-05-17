package com.example.surimusakotlin.domain.usecase.bottomSheet

import com.example.surimusakotlin.data.repository.ProductRepository

class DeleteProductUseCase(private val repository: ProductRepository) {
    suspend fun execute(id: Long) {
        repository.deleteProductById(id)
    }
}