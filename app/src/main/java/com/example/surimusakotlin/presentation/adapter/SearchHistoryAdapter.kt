package com.example.surimusakotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surimusakotlin.R
import com.example.surimusakotlin.presentation.search.SearchHistoryManager

class SearchHistoryAdapter() : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>(),
    SearchHistoryManager.SearchHistoryListener {
     var historyList: List<String> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_history_view, parent, false)
        return SearchHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val historyTextView: TextView = itemView.findViewById(R.id.histoty_element_text)

        fun bind(query: String) {
            historyTextView.text = query
        }
    }

    override fun onHistoryUpdated(history: List<String>) {
        historyList = history.toList()
        notifyDataSetChanged()
    }

    interface DeleteManager {
        fun deleteById(position: Int)
    }

}
