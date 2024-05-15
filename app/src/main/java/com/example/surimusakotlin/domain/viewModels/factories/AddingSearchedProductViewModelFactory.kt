package com.example.surimusakotlin.domain.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surimusakotlin.domain.usecase.addingSearchedProduct.AddingSearchedProductUseCase
import com.example.surimusakotlin.domain.viewModels.AddingSearchedProductViewModel

class AddingSearchedProductViewModelFactory(
    private val addingSearchedProductUseCase: AddingSearchedProductUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddingSearchedProductViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AddingSearchedProductViewModel(addingSearchedProductUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}