package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surimusakotlin.domain.usecase.addProduct.MaintainEatingRecordsUseCase

class AddProductOrMealViewModelFactory(
    private val maintainEatingRecordsUseCase: MaintainEatingRecordsUseCase
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProductOrMealViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AddProductOrMealViewModel(maintainEatingRecordsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}