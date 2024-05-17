package com.example.surimusakotlin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.surimusakotlin.data.database.Dao.Converters
import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.data.database.Entities.Total_nutritions
import com.example.surimusakotlin.data.database.Entities.UserInfo

@Database(entities = [Total_nutritions::class, Eating::class, Product::class, UserInfo::class], version = 2)
@TypeConverters(Converters::class)
abstract class MainDB : RoomDatabase() {
    abstract fun totalNutritionDao(): TotalNutritionDao

    companion object {
        @Volatile
        private var INSTANCE: MainDB? = null

        fun getDB(context: Context): MainDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDB::class.java,
                    "main.db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}