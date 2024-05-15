package com.example.surimusakotlin.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(

    @PrimaryKey(autoGenerate = true)
    var id : Long? = null,
    @ColumnInfo(name = "productName")
    var productName : String? = null,
    @ColumnInfo(name = "calories")
    var calories : Double? = null,
    @ColumnInfo(name = "cholesterol")
    val cholesterol : Double? = null,
    @ColumnInfo(name = "dietaryFiber")
    var dietaryFiber: Double? = null,
    @ColumnInfo(name = "potassium")
    var potassium: Double? = null,
    @ColumnInfo(name = "protein")
    var protein: Double? = null,
    @ColumnInfo(name = "saturated_fat")
    var saturated_fat: Double? = null,
    @ColumnInfo(name = "sodium")
    var sodium: Double? = null,
    @ColumnInfo(name = "sugars")
    var sugars: Double? = null,
    @ColumnInfo(name = "totalCarbohydrate")
    var totalCarbohydrate: Double? = null,
    @ColumnInfo(name = "totalFat")
    var totalFat: Double? = null,
    @ColumnInfo(name = "grams")
    var grams: Double? = null,
    @ColumnInfo(name = "mealId")
    var mealId: Long? = null

)

