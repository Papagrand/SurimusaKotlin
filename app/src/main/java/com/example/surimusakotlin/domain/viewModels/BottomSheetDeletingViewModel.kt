package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.domain.usecase.bottomSheet.DeleteProductUseCase
import com.example.surimusakotlin.domain.usecase.bottomSheet.GetProductsInAddProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetDeletingViewModel(

    private val getProductsInAddProductUseCase: GetProductsInAddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase

): ViewModel() {

    private val _productData = MutableLiveData<List<Product>>()
    val productData: LiveData<List<Product>> get() = _productData

    fun getProductsFromThisEating(mealId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            getProductsInAddProductUseCase.execute(mealId).observeForever{productList ->
                _productData.postValue(productList)
            }
        }
    }
    fun deleteProduct(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteProductUseCase.execute(id)
        }
    }

}