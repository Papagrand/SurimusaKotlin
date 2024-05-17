package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.domain.usecase.addProduct.AddProductsDataUseCase
import com.example.surimusakotlin.domain.usecase.addProduct.MaintainEatingRecordsUseCase
import com.example.surimusakotlin.domain.usecase.bottomSheet.GetProductsInAddProductUseCase
import com.example.surimusakotlin.domain.usecase.progress.GetEatingDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProductOrMealViewModel(
    private val maintainEatingRecordsUseCase: MaintainEatingRecordsUseCase,
    private val addProductsDataUseCase: AddProductsDataUseCase,
    private val getEatingDataUseCase: GetEatingDataUseCase,
    private val getProductsInAddProductUseCase: GetProductsInAddProductUseCase
) : ViewModel() {

    private val _eatingData = MutableLiveData<Eating?>()
    val eatingData: LiveData<Eating?> get() = _eatingData

    private val _productData = MutableLiveData<List<Product>>()
    val productData: LiveData<List<Product>> get() = _productData

    fun maintainRecordsAddProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            maintainEatingRecordsUseCase.execute()
        }
    }

    fun getAllProductsFromThisEating(mealId: Long) {
        val eatingWithProductsLiveData = addProductsDataUseCase.addInformationOfProducts(mealId)
        eatingWithProductsLiveData.observeForever { eating ->
            _eatingData.value = eating
        }
    }

    fun getEatingCurrentData(mealId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            getEatingDataUseCase.execute(mealId).observeForever { eating ->
                _eatingData.value = eating
            }
        }
    }

    fun getCountProducts(mealId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            getProductsInAddProductUseCase.execute(mealId).observeForever { products ->
                _productData.value = products
            }
        }
    }
}