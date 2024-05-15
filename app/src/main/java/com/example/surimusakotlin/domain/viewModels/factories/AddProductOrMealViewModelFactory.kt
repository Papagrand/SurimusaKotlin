package com.example.surimusakotlin.domain.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surimusakotlin.domain.usecase.addProduct.AddProductsDataUseCase
import com.example.surimusakotlin.domain.usecase.addProduct.MaintainEatingRecordsUseCase
import com.example.surimusakotlin.domain.usecase.progress.GetEatingDataUseCase
import com.example.surimusakotlin.domain.viewModels.AddProductOrMealViewModel

class AddProductOrMealViewModelFactory(
    private val maintainEatingRecordsUseCase: MaintainEatingRecordsUseCase,
    private val addProductsDataUseCase: AddProductsDataUseCase,
    private val getEatingDataUseCase: GetEatingDataUseCase
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProductOrMealViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AddProductOrMealViewModel(
                maintainEatingRecordsUseCase,
                addProductsDataUseCase,
                getEatingDataUseCase

            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}