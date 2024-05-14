package com.example.surimusakotlin.data.database.Dao

import androidx.room.TypeConverter
import com.example.surimusakotlin.data.database.Entities.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Product>? {
        return if (value == null) {
            null
        } else {
            val listType: Type = object : TypeToken<List<Product>>() {}.type
            Gson().fromJson(value, listType)
        }
    }

    @TypeConverter
    fun fromArrayList(list: List<Product?>?): String? {
        return Gson().toJson(list)
    }
}