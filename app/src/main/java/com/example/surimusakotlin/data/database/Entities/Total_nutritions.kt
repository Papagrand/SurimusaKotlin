package com.example.surimusakotlin.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "total_nutritions")
data class Total_nutritions(

    @PrimaryKey
    var id: Long? = null,
    @ColumnInfo(name = "totalcaloriesDayEated")
    var totalcaloriesDayEated: Double? = 0.0,
    @ColumnInfo(name = "totalDayCalories")
    var totalDayCalories : Double? = 0.0,
    @ColumnInfo(name = "totalDayProtein")
    var totalDayProtein: Double? = 0.0,
    @ColumnInfo(name = "totalDayCarbohydrate")
    var totalDayCarbohydrate: Double? = 0.0,
    @ColumnInfo(name = "totalDayFat")
    var totalDayFat: Double? = 0.0,

)
