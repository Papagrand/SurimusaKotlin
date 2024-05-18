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
import com.example.surimusakotlin.domain.usecase.progress.UpdateDayWaterUsecase
import com.example.surimusakotlin.domain.usecase.progress.UpdateTotalDayNutrientsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val getTotalNutritionUseCase: GetTotalNutritionUseCase,
    private val maintainTotalNutritionRecordsUseCase: MaintainTotalNutritionRecordsUseCase,
    private val getEatingDataUseCase: GetEatingDataUseCase,
    private val updateTotalDayNutrientsUseCase: UpdateTotalDayNutrientsUseCase,
    private val updateDayWaterUsecase: UpdateDayWaterUsecase
) : ViewModel() {
    private val _totalCalories = MutableLiveData<Double>()
    val totalCalories: LiveData<Double> = _totalCalories
    private val _totalDayProtein = MutableLiveData<Double>()
    val totalDayProtein: LiveData<Double> = _totalDayProtein
    private val _totalDayCarbohydrate = MutableLiveData<Double>()
    val totalDayCarbohydrate: LiveData<Double> = _totalDayCarbohydrate
    private val _totalDayFat = MutableLiveData<Double>()
    val totalDayFat: LiveData<Double> = _totalDayFat
    private val _totalWater = MutableLiveData<Double>()
    val totalWater: LiveData<Double> = _totalWater

    init {
        _totalCalories.value = 0.0
        _totalDayProtein.value = 0.0
        _totalDayCarbohydrate.value = 0.0
        _totalDayFat.value = 0.0
        _totalWater.value = 0.0
    }

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

    fun setDefaultNutrients(calories: Double, protein: Double,carbohydrate: Double, fat: Double,water: Double) {
        _totalCalories.value = calories
        _totalDayCarbohydrate.value = carbohydrate
        _totalDayProtein.value = protein
        _totalDayFat.value = fat
        _totalWater.value = water

    }
    fun updateTotalDayNutrients(calories: Double, protein: Double, carbohydrate: Double, fat: Double, id: Long) {
        _totalCalories.value = (_totalCalories.value ?: 0.0) +  calories
        _totalDayCarbohydrate.value = (_totalDayCarbohydrate.value ?: 0.0) +  carbohydrate
        _totalDayProtein.value = (_totalDayProtein.value ?: 0.0) +  protein
        _totalDayFat.value = (_totalDayFat.value ?: 0.0) +  fat
        viewModelScope.launch(Dispatchers.IO) {
            updateTotalDayNutrientsUseCase.updateTotalDayNutrients(totalCalories.value, totalDayProtein.value,  totalDayCarbohydrate.value, totalDayFat.value, id)
        }
    }
    fun updateDayWater(water: Double, id: Long){
        _totalWater.value = (_totalWater.value ?: 0.0) + water
        viewModelScope.launch(Dispatchers.IO) {
            updateDayWaterUsecase.updateDayWater(totalWater.value, id)
        }
    }

    fun loadNutritionData(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getTotalNutritionUseCase.execute(id)

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
