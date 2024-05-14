package com.example.surimusakotlin.domain.usecase.addProduct

import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.Entities.Total_nutritions
import com.example.surimusakotlin.data.repository.EatingRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MaintainEatingRecordsUseCase(
    private val repository: EatingRepository
) {

    suspend fun execute() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val eating = repository.getAllEating()

        for (i in 0 until 90) {
            val dateStr = dateFormat.format(calendar.time)
            val dateId = try {
                dateStr.toLong()
            } catch (e: NumberFormatException) {
                continue
            }
            for (j in 1 until 5){
                if (eating.none { it.id == dateId }) {
                    repository.insertEating(Eating(id = dateId*10+j))
                }
            }
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }

        if (eating.size > 360) {
            val toDelete = eating.sortedByDescending { it.id }.drop(360)
            toDelete.forEach { repository.deleteEating(it) }
        }
    }

}