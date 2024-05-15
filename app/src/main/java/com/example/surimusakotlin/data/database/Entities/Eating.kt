package com.example.surimusakotlin.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eating")
data class Eating (
    @PrimaryKey
    var id: Long? = null,
    @ColumnInfo(name = "totalCalories")
    var totalCaloriesEating : Double? = 0.0,
    @ColumnInfo(name = "totalFats")
    var totalFatsEating: Double? = 0.0,
    @ColumnInfo(name = "totalCarbohydrate")
    var totalCarbohydrateEating: Double? = 0.0,
    @ColumnInfo(name = "totalProteins")
    var totalProteinsEating: Double? = 0.0,
    @ColumnInfo(name = "nameProducts")
    var nameProducts: String? = null,
    @ColumnInfo(name = "countProducts")
    var countProducts: Int = 0,
    @ColumnInfo(name = "products")
    var products: List<Product>? = null
)
