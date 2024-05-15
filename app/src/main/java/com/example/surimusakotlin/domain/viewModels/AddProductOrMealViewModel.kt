package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.domain.usecase.addProduct.AddProductsDataUseCase
import com.example.surimusakotlin.domain.usecase.addProduct.MaintainEatingRecordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddProductOrMealViewModel(

    private val maintainEatingRecordsUseCase: MaintainEatingRecordsUseCase,
    private val addProductsDataUseCase: AddProductsDataUseCase

):ViewModel() {
    private val _eatingData = MutableLiveData<Eating?>()
    val eatingData: LiveData<Eating?> get() = _eatingData

    fun maintainRecordsAddProduct(){
        viewModelScope.launch(Dispatchers.IO) {
            maintainEatingRecordsUseCase.execute()
        }
    }

    fun getAllProductsFromThisEating(mealId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            addProductsDataUseCase.addInformationOfProducts(mealId)
        }
    }
}