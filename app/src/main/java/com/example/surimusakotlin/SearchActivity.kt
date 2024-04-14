package com.example.surimusakotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.model.FoodInstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchActivity : AppCompatActivity() {
    val foodRepository = FoodRepository()
    val searchViewModel = SearchViewModel(foodRepository)
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var searchText: String? = null
    private val foodAdapter = FoodAdapter()
    private var searchJob: Job? = null
    private val searchScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        searchView = findViewById(R.id.search_view)
        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString("searchText")
            searchView.setQuery(searchText, false)
        }

        recyclerView = findViewById(R.id.food_recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = foodAdapter

        val backButton = findViewById<ImageButton>(R.id.back_to_search_meal)
        backButton.setOnClickListener {
            val intent = Intent(this@SearchActivity, FourthActivity::class.java)
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {
            searchViewModel.foodInstantList.collectLatest {
                foodAdapter.clearList()
                foodAdapter.setList(it)
                foodAdapter.notifyDataSetChanged()
            }
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchText = newText
                searchJob?.cancel()
                if (!newText.isNullOrEmpty()) {
                    searchJob = GlobalScope.launch(Dispatchers.Main) {
                        delay(1000)
                        searchViewModel.makeRequest(newText)
                    }
                }
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        searchScope.cancel()
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