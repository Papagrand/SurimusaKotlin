package com.example.surimusakotlin.di

import com.example.surimusakotlin.domain.usecase.search.GetFoodInstantResponceByQueryUseCase
import com.example.surimusakotlin.domain.usecase.search.GetNutritionsForCommonListUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetFoodInstantResponceByQueryUseCase> {
        GetFoodInstantResponceByQueryUseCase(foodRepositoryInterface = get())
    }

    factory<GetNutritionsForCommonListUseCase> {
        GetNutritionsForCommonListUseCase(foodRepositoryInterface = get())
    }

}