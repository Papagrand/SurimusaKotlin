package com.example.surimusakotlin.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surimusakotlin.data.repository.FoodRepository

class SearchViewModelFactory(
    private val foodRepository: FoodRepository,
    private val searchHistoryManager: SearchHistoryManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            foodRepository = foodRepository,
            searchHistoryManager = searchHistoryManager
        ) as T
    }
}