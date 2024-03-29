package com.example.surimusakotlin

import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FourthActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private var searchText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_or_meal)
        val meal = intent.getStringExtra("meal")
        val breakfastLunchDinnerTextView = findViewById<TextView>(R.id.breakfast_lunch_dinner)
        breakfastLunchDinnerTextView.text = meal


        searchView = findViewById(R.id.search_view)

        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString("searchText")
            searchView.setQuery(searchText, false)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchText = newText
                return true
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchText", searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString("searchText")
    }
}
