package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.domain.FoodNutrientsManager
import com.example.surimusakotlin.domain.usecase.addingSearchedProduct.AddingSearchedProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddingSearchedProductViewModel(private val addingSearchedProductUseCase: AddingSearchedProductUseCase): ViewModel() {

    private val _productData = MutableLiveData<Product?>()
    val productData: LiveData<Product?> get() = _productData

    fun addProduct(foodNutrientsManager: FoodNutrientsManager, mealId: Long){
        viewModelScope.launch(Dispatchers.IO) {

            addingSearchedProductUseCase.addProduct(foodNutrientsManager, mealId)

        }

    }

}