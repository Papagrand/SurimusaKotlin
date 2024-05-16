package com.example.surimusakotlin.data.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.data.database.Entities.Total_nutritions
import java.util.concurrent.Flow

@Dao
interface TotalNutritionDao {
    //ProgressFragment
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTotalNutritions(nutrition: Total_nutritions)

    @Query("SELECT * FROM total_nutritions WHERE id = :id")
    fun getTotalNutritionById(id: Long): Total_nutritions?

    @Query("SELECT * FROM total_nutritions ORDER BY id DESC")
    fun getAllNutritions(): List<Total_nutritions>

    @Delete
    fun deleteNutrition(nutrition: Total_nutritions)

    //Add Fragment
    @Query("SELECT * FROM eating WHERE id = :id")
    fun getEatingById(id: Long): Eating?

    @Query("SELECT * FROM Eating WHERE id = :id")
    fun getEatingByIdLiveData(id: Long): LiveData<Eating?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEating(eating: Eating)

    @Query("SELECT * FROM eating ORDER BY id DESC")
    fun getEating(): List<Eating>

    @Delete
    fun deleteEating(eating: Eating)

    @Update
    fun updateEating(eating: Eating)

    //AddingSearched fragment
    @Query("SELECT * FROM product WHERE mealId = :mealId")
    fun getProductByMealId(mealId: Long): List<Product>?

    @Insert
    fun insertProduct(product: Product)

}