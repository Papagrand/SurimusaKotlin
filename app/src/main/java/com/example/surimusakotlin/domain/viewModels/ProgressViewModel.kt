package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.Entities.Total_nutritions
import com.example.surimusakotlin.domain.usecase.progress.GetEatingDataUseCase
import com.example.surimusakotlin.domain.usecase.progress.GetTotalNutritionUseCase
import com.example.surimusakotlin.domain.usecase.progress.MaintainTotalNutritionRecordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val getTotalNutritionUseCase: GetTotalNutritionUseCase,
    private val maintainTotalNutritionRecordsUseCase: MaintainTotalNutritionRecordsUseCase,
    private val getEatingDataUseCase: GetEatingDataUseCase
) : ViewModel() {

    private val _nutritionData = MutableLiveData<Total_nutritions?>()
    val nutritionData: LiveData<Total_nutritions?> get() = _nutritionData

    private val _eatingData = MutableLiveData<Eating?>()
    val eatingData: LiveData<Eating?> get() = _eatingData

    private val _breakfastData = MutableLiveData<Eating?>()
    val breakfastData: LiveData<Eating?> = _breakfastData

    private val _lunchData = MutableLiveData<Eating?>()
    val lunchData: LiveData<Eating?> = _lunchData

    private val _dinnerData = MutableLiveData<Eating?>()
    val dinnerData: LiveData<Eating?> = _dinnerData

    private val _snackData = MutableLiveData<Eating?>()
    val snackData: LiveData<Eating?> = _snackData

    fun loadNutritionData(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val nutrition = getTotalNutritionUseCase.execute(id)
            _nutritionData.postValue(nutrition)
        }
    }
    fun maintainRecordsProgress() {
        viewModelScope.launch(Dispatchers.IO) {
            maintainTotalNutritionRecordsUseCase.execute()
        }
    }

    fun getEatingData(mealId: Long, mealType: String) {
        viewModelScope.launch {
            getEatingDataUseCase.execute(mealId).observeForever { eatingData ->
                when (mealType) {
                    "breakfast" -> _breakfastData.postValue(eatingData)
                    "lunch" -> _lunchData.postValue(eatingData)
                    "dinner" -> _dinnerData.postValue(eatingData)
                    "snack" -> _snackData.postValue(eatingData)
                }
            }
        }
    }

}
