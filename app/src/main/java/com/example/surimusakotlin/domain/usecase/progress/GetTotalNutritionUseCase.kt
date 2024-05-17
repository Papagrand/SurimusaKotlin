package com.example.surimusakotlin.domain.usecase.progress

import androidx.lifecycle.LiveData
import com.example.surimusakotlin.data.database.Entities.Total_nutritions
import com.example.surimusakotlin.data.repository.TotalNutritionRepository

class GetTotalNutritionUseCase(private val repository: TotalNutritionRepository) {
    fun execute(id: Long): Total_nutritions? {
        return repository.getTotalNutritionById(id)
    }
}