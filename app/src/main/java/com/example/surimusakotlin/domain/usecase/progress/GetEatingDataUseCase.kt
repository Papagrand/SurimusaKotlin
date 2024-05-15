package com.example.surimusakotlin.domain.usecase.progress

import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.repository.EatingRepository

class GetEatingDataUseCase(private val repository: EatingRepository) {
    fun execute(id: Long): Eating?{
        return repository.getEatingById(id)
    }
}