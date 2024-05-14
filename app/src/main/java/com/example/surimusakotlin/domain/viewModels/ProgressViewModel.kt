package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surimusakotlin.data.database.Entities.Total_nutritions
import com.example.surimusakotlin.domain.usecase.progress.GetTotalNutritionUseCase
import com.example.surimusakotlin.domain.usecase.progress.MaintainTotalNutritionRecordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val getTotalNutritionUseCase: GetTotalNutritionUseCase,
    private val maintainTotalNutritionRecordsUseCase: MaintainTotalNutritionRecordsUseCase
) : ViewModel() {

    private val _nutritionData = MutableLiveData<Total_nutritions?>()
    val nutritionData: LiveData<Total_nutritions?> get() = _nutritionData

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
}
