package com.example.surimusakotlin.di

import com.example.surimusakotlin.presentation.search.SearchHistoryManager
import com.example.surimusakotlin.domain.repository.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<SearchViewModel>(){
        SearchViewModel(
            searchHistoryManager = SearchHistoryManager(context = androidContext()),
            getFoodInstantResponceByQueryUseCase = get(),
            getNutritionsForCommonListUseCase = get()
        )
    }
}