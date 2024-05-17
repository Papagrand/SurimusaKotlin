package com.example.surimusakotlin.domain.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surimusakotlin.domain.usecase.bottomSheet.DeleteProductUseCase
import com.example.surimusakotlin.domain.usecase.bottomSheet.GetProductsInAddProductUseCase
import com.example.surimusakotlin.domain.viewModels.BottomSheetDeletingViewModel

class BottomSheetDeletingViewModelFactory(
    private val getProductsInAddProductUseCase: GetProductsInAddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomSheetDeletingViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return BottomSheetDeletingViewModel(
                getProductsInAddProductUseCase,
                deleteProductUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}