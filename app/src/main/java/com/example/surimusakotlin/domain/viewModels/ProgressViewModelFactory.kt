package com.example.surimusakotlin.domain.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surimusakotlin.domain.usecase.progress.GetTotalNutritionUseCase
import com.example.surimusakotlin.domain.usecase.progress.MaintainTotalNutritionRecordsUseCase

class ProgressViewModelFactory(
    private val getTotalNutritionUseCase: GetTotalNutritionUseCase,
    private val maintainTotalNutritionRecordsUseCase: MaintainTotalNutritionRecordsUseCase

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgressViewModel(getTotalNutritionUseCase, maintainTotalNutritionRecordsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
