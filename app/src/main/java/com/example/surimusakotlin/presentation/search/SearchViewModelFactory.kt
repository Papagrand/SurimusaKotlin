package com.example.surimusakotlin.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.domain.usecase.GetFoodInstantResponceByQueryUseCase
import com.example.surimusakotlin.domain.usecase.GetNutritionsForCommonListUseCase

//class SearchViewModelFactory(
//    private val foodRepository: FoodRepository,
//    private val searchHistoryManager: SearchHistoryManager,
//    private val getNutritionsForCommonListUseCase: GetNutritionsForCommonListUseCase,
//    private val getFoodInstantResponceByQueryUseCase: GetFoodInstantResponceByQueryUseCase
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return SearchViewModel(
//            foodRepository = foodRepository,
//            searchHistoryManager = searchHistoryManager,
//            getNutritionsForCommonListUseCase = getNutritionsForCommonListUseCase,
//            getFoodInstantResponceByQueryUseCase = getFoodInstantResponceByQueryUseCase
//        ) as T
//    }
//}