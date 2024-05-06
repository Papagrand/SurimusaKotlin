package com.example.surimusakotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.surimusakotlin.data.ScreenSwitchable
import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.databinding.ActivitySearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchActivity : AppCompatActivity(), SearchHistoryAdapter.DeleteManager, ScreenSwitchable {
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    private var searchJob: Job? = null

    private lateinit var binding: ActivitySearchBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchViewModel = viewModels<SearchViewModel> {
            SearchViewModelFactory(
                FoodRepository(), SearchHistoryManager(this)
            )
        }.value

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initializeFoodRecyclerView()
        initializeSearchHistoryRecyclerView()
        searchViewModel.searchHistoryManager.registerListener(searchHistoryAdapter)
        val backButton = binding.backToSearchMeal
        backButton.setOnClickListener {
            val intent = Intent(this@SearchActivity, FourthActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.searchEditText.addTextChangedListener { s ->
            searchJob?.cancel()
            if (!s.isNullOrEmpty()) {
                hideSearchHistory()
                showDeleteCross()
                searchJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(1000)
                    searchViewModel.makeFoodInstantRequestByQuery(s.toString())
                }
            } else {
                hideDeleteCross()
                showSearchHistory()
            }
        }

        binding.clearButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }

        lifecycleScope.launch {
            searchViewModel.isError.collectLatest { isError ->
                if (isError) {
                    showError()
                } else hideError()
            }
        }

        lifecycleScope.launch {
            searchViewModel.isLoading.collectLatest { isLoading ->
                if (isLoading) {
                   showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        lifecycleScope.launch {
            searchViewModel.foodInstantList.collectLatest { list ->
                hideSearchHistory()
                showData()

                if (list.isEmpty() && binding.searchEditText.text.isNotEmpty()) {
                    foodAdapter.listFood = emptyList()
                    showNoData()
                } else if (list.isEmpty() && binding.searchEditText.text.isEmpty()) {
                    foodAdapter.listFood = emptyList()
                    showSearchHistory()
                } else {
                    foodAdapter.listFood = list
                    showFoodRecyclerView()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchViewModel.searchHistoryManager.unregisterListener(searchHistoryAdapter)
    }

    fun onClickReload(view: View) {
        val searchText = binding.searchEditText.text.toString()
        searchJob?.cancel()
        if (searchText.isNotEmpty()) {
            searchJob = lifecycleScope.launch(Dispatchers.Main) {
                delay(1000)
                searchViewModel.makeFoodInstantRequestByQuery(searchText)
            }
        }
    }

    fun onClickClear(view: View) {
        searchViewModel.searchHistoryManager.clearSearchHistory()
    }

    override fun showError() {
        binding.noWifi.root.visibility = View.VISIBLE
    }

    override fun showNoData() {
        binding.noData.root.visibility = View.VISIBLE
    }

    override fun hideError() {
        binding.noWifi.root.visibility = View.GONE
    }

    override fun showData() {
        binding.noData.root.visibility = View.GONE
    }

    override fun showDeleteCross() {
        binding.clearButton.visibility = View.VISIBLE
    }

    override fun hideDeleteCross() {
        binding.clearButton.visibility = View.GONE
    }
    fun hideLoading() {
        binding.loadingInfo.root.visibility = View.GONE
    }
    fun showLoading() {
        binding.loadingInfo.root.visibility = View.VISIBLE
    }

    override fun showSearchHistory() {
        searchHistoryAdapter.onHistoryUpdated(searchViewModel.searchHistoryManager.getSearchHistory())
        binding.searchHistoryRecyclerView.visibility = View.VISIBLE
        binding.clearHistory.visibility = View.VISIBLE
    }

    override fun hideSearchHistory() {
        binding.searchHistoryRecyclerView.visibility = View.GONE
        binding.clearHistory.visibility = View.GONE
    }

    override fun showFoodRecyclerView() {
        binding.foodRecyclerView.visibility = View.VISIBLE
    }

    override fun hideFoodRecyclerView() {
        binding.foodRecyclerView.visibility = View.GONE
    }

    private fun initializeFoodRecyclerView() {
        foodAdapter = FoodAdapter()
        with(binding.foodRecyclerView) {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = foodAdapter
        }
    }

    private fun initializeSearchHistoryRecyclerView() {
        searchHistoryAdapter =
            SearchHistoryAdapter(searchViewModel.searchHistoryManager.getSearchHistory(), this)
        showSearchHistory()
        binding.searchHistoryRecyclerView.apply {
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchHistoryAdapter
        }
    }

    override fun deleteById(position: Int) {
        searchViewModel.deleteHistoryItemById(position)
    }
}
