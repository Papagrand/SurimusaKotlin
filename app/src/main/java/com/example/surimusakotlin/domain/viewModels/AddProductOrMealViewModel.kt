package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.domain.usecase.addProduct.AddProductsDataUseCase
import com.example.surimusakotlin.domain.usecase.addProduct.MaintainEatingRecordsUseCase
import com.example.surimusakotlin.domain.usecase.progress.GetEatingDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProductOrMealViewModel(

    private val maintainEatingRecordsUseCase: MaintainEatingRecordsUseCase,
    private val addProductsDataUseCase: AddProductsDataUseCase,
    private val getEatingDataUseCase: GetEatingDataUseCase

):ViewModel() {
    private val _eatingData = MutableLiveData<Eating?>()
    val eatingData: LiveData<Eating?> get() = _eatingData

    fun maintainRecordsAddProduct(){
        viewModelScope.launch(Dispatchers.IO) {
            maintainEatingRecordsUseCase.execute()
        }
    }

    fun getAllProductsFromThisEating(mealId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            addProductsDataUseCase.addInformationOfProducts(mealId)
            getEatingCurrentData(mealId)
        }
    }
    fun getEatingCurrentData(mealId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            getEatingDataUseCase.execute(mealId).observeForever { eating ->
                _eatingData.postValue(eating)
            }

        }
    }
}