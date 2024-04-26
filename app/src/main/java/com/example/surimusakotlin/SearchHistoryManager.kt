package com.example.surimusakotlin

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistoryManager(private val context: Context) {

    interface SearchHistoryListener {
        fun onHistoryUpdated(history: List<String>)
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
            history.removeAt(history.size - 1)
        }
        saveSearchHistory(history)
        notifyListeners()
    }

    fun clearSearchHistory() {
        saveSearchHistory(emptyList())
        notifyListeners()
    }

    private fun notifyListeners() {
        val history = getSearchHistory()
        listeners.forEach { it.onHistoryUpdated(history) }
    }

    private fun saveSearchHistory(history: List<String>) {
        sharedPreferences.edit().putString(KEY_SEARCH_HISTORY, Gson().toJson(history)).apply()
    }

    fun getSearchHistory(): List<String> {
        val json = sharedPreferences.getString(KEY_SEARCH_HISTORY, null)
        return if (json != null) {
            Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun deleteById(position: Int) {

    }

    companion object {
        private const val PREF_NAME = "SearchHistoryPrefs"
        private const val KEY_SEARCH_HISTORY = "search_history"
        private const val MAX_HISTORY_SIZE = 10
    }
}
