package com.example.surimusakotlin

import android.content.Context
import android.content.SharedPreferences

class SearchHistoryManager(private val context: Context) {

    interface SearchHistoryListener {
        fun onHistoryUpdated(history: Set<String>)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private val listeners = mutableListOf<SearchHistoryListener>()

    fun registerListener(listener: SearchHistoryListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: SearchHistoryListener) {
        listeners.remove(listener)
    }

    fun addSearchQuery(query: String) {
        val history = getSearchHistory().toMutableList()
        history.remove(query)
        history.add(0, query)
        if (history.size > MAX_HISTORY_SIZE) {
            history.subList(MAX_HISTORY_SIZE, history.size).clear()
        }
        saveSearchHistory(history.toSet())
        notifyListeners()
    }

    fun clearSearchHistory() {
        saveSearchHistory(emptySet())
        notifyListeners()
    }

    private fun notifyListeners() {
        val history = getSearchHistory()
        listeners.forEach { it.onHistoryUpdated(history) }
    }

    fun getSearchHistory(): Set<String> {
        return sharedPreferences.getStringSet(KEY_SEARCH_HISTORY, emptySet()) ?: emptySet()
    }


    private fun saveSearchHistory(history: Set<String>) {
        sharedPreferences.edit().putStringSet(KEY_SEARCH_HISTORY, history).apply()
    }

    fun deleteById(position: Int) {

    }

    companion object {
        private const val PREF_NAME = "SearchHistoryPrefs"
        private const val KEY_SEARCH_HISTORY = "search_history"
        private const val MAX_HISTORY_SIZE = 10
    }
}
