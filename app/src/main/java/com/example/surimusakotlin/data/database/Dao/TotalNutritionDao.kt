package com.example.surimusakotlin.data.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.Entities.Total_nutritions

@Dao
interface TotalNutritionDao {
    @Insert
    fun insertTotalNutritions(nutrition: Total_nutritions)

    @Query("SELECT * FROM total_nutritions WHERE id = :id")
    fun getTotalNutritionById(id: Long): Total_nutritions?

    @Query("SELECT * FROM total_nutritions ORDER BY id DESC")
    fun getAllNutritions(): List<Total_nutritions>

    @Delete
    fun deleteNutrition(nutrition: Total_nutritions)

    @Query("SELECT * FROM eating WHERE id = :id")
    fun getEatingById(id: Long): Eating?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEating(eating: Eating)

    @Query("SELECT * FROM eating ORDER BY id DESC")
    fun getEating(): List<Eating>

    @Delete
    fun deleteEating(eating: Eating)

}