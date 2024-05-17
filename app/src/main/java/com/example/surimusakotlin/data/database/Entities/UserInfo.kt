package com.example.surimusakotlin.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userInfo")
data class UserInfo (

    @PrimaryKey
    var id: Long = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "sex")
    var sex: String = "",
    @ColumnInfo(name = "heigh")
    var heigh: Int = 0,
    @ColumnInfo(name = "weight")
    var weight: Float = 0.0F,
    @ColumnInfo(name = "email")
    var email: String = ""

)