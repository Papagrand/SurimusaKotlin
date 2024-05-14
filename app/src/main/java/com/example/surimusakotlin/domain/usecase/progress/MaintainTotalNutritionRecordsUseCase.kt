package com.example.surimusakotlin.domain.usecase.progress

import com.example.surimusakotlin.data.database.Entities.Total_nutritions
import com.example.surimusakotlin.data.repository.TotalNutritionRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MaintainTotalNutritionRecordsUseCase(
    private val repository: TotalNutritionRepository
) {
    suspend fun execute() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val nutritions = repository.getAllNutritions()

        for (i in 0 until 90) {
            val dateStr = dateFormat.format(calendar.time)
            val dateId = try {
                dateStr.toLong()
            } catch (e: NumberFormatException) {
                continue
            }

            if (nutritions.none { it.id == dateId }) {
                repository.insertTotalNutrition(Total_nutritions(id = dateId))
            }
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }

        if (nutritions.size > 90) {
            val toDelete = nutritions.sortedByDescending { it.id }.drop(90)
            toDelete.forEach { repository.deleteTotalNutrition(it) }
        }
    }
}