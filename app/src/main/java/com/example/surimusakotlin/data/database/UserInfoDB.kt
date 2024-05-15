package com.example.surimusakotlin.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.surimusakotlin.data.database.Dao.UserInfoDao

abstract class UserInfoDB : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        @Volatile
        private var INSTANCE: UserInfoDB? = null

        fun getDB(context: Context): UserInfoDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserInfoDB::class.java,
                    "main.db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}