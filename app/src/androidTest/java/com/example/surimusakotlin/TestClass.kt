package com.example.surimusakotlin

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.surimusakotlin.data.database.Dao.TotalNutritionDao
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.data.database.MainDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith

@RunWith(JUnit4ClassRunner::class)
class TestClass {
    private lateinit var database: MainDB
    private lateinit var requestDao: TotalNutritionDao

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            MainDB::class.java
        ).build()

        requestDao = database.totalNutritionDao()
    }

    @Test
    fun should_insert_data_in_db()= runBlocking(Dispatchers.Main) {
        val entity = Product()
        withContext(Dispatchers.IO) {
            requestDao.insertProduct(entity)
        }
        val a = requestDao.getProductsById(1)
        entity.id = 1

        a.observeForever {
            Assert.assertEquals(entity, it.first())
        }
    }

    @After
    fun clear() {
        database.close()
    }
}