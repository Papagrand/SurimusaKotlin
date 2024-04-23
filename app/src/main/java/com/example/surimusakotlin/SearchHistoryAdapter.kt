package com.example.surimusakotlin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchHistoryAdapter(
    private var historyList: List<String>,
    private var deleteManager: DeleteManager
) : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>(),
    SearchHistoryManager.SearchHistoryListener {

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

    override fun onHistoryUpdated(history: Set<String>) {
        historyList = history.toList()
        notifyDataSetChanged()
    }

    interface DeleteManager {
        fun deleteById(position: Int)
    }

}
