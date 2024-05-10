package com.example.surimusakotlin.di

import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.domain.repository.FoodRepositoryInterface
import org.koin.dsl.module

val dataModule = module {


    single <FoodRepositoryInterface>{
        FoodRepository()
    }


}