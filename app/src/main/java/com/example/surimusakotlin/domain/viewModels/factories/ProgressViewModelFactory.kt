package com.example.surimusakotlin.domain.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surimusakotlin.domain.usecase.progress.GetEatingDataUseCase
import com.example.surimusakotlin.domain.usecase.progress.GetTotalNutritionUseCase
import com.example.surimusakotlin.domain.usecase.progress.MaintainTotalNutritionRecordsUseCase
import com.example.surimusakotlin.domain.usecase.progress.UpdateDayWaterUsecase
import com.example.surimusakotlin.domain.usecase.progress.UpdateTotalDayNutrientsUseCase
import com.example.surimusakotlin.domain.viewModels.ProgressViewModel

class ProgressViewModelFactory(
    private val getTotalNutritionUseCase: GetTotalNutritionUseCase,
    private val maintainTotalNutritionRecordsUseCase: MaintainTotalNutritionRecordsUseCase,
    private val getEatingDataUseCase: GetEatingDataUseCase,
    private val updateTotalDayNutrientsUseCase: UpdateTotalDayNutrientsUseCase,
    private val updateDayWaterUsecase: UpdateDayWaterUsecase

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgressViewModel(
                getTotalNutritionUseCase,
                maintainTotalNutritionRecordsUseCase,
                getEatingDataUseCase,
                updateTotalDayNutrientsUseCase,
                updateDayWaterUsecase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
