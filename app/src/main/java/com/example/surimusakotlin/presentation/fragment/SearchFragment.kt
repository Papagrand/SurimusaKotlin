package com.example.surimusakotlin.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.surimusakotlin.data.ScreenSwitchable
import com.example.surimusakotlin.databinding.FragmentSearchBinding
import com.example.surimusakotlin.domain.model.Food
import com.example.surimusakotlin.presentation.adapter.FoodAdapter
import com.example.surimusakotlin.presentation.adapter.SearchHistoryAdapter
import com.example.surimusakotlin.domain.viewModels.SearchViewModel
//import com.example.surimusakotlin.presentation.search.SearchViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), SearchHistoryAdapter.DeleteManager, ScreenSwitchable {
    private val searchViewModel by viewModel<SearchViewModel>()
    private val arg by navArgs<SearchFragmentArgs>()

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    private var searchJob: Job? = null

    private lateinit var binding: FragmentSearchBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            findNavController().popBackStack()
        }
        binding.reloadText.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            searchJob?.cancel()
            if (searchText.isNotEmpty()) {
                searchJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(1000)
                    searchViewModel.makeFoodInstantRequestByQuery(searchText)
                }
            }
        }

        binding.clearHistory.setOnClickListener {
            searchViewModel.searchHistoryManager.clearSearchHistory()
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
                lifecycleScope.launch{
                    foodAdapter.listFood = emptyList()
                }
                hideDeleteCross()
                showData()
                hideError()
                hideLoading()
                showSearchHistory()
            }
        }
        binding.clearButton.setOnClickListener {
            binding.searchEditText.text.clear()
            lifecycleScope.launch{
                foodAdapter.listFood = emptyList()
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
            searchViewModel.isError.collectLatest { isError ->
                if (isError) {
                    showError()
                } else hideError()
            }
        }
        lifecycleScope.launch {
            searchViewModel.isNoData.collectLatest { isNoData ->
                if (isNoData) {
                    showNoData()
                } else showData()
            }
        }
        lifecycleScope.launch {
            searchViewModel.foodInstantList.collectLatest { list ->
                hideSearchHistory()
                showData()
                foodAdapter.listFood = emptyList()
                if (list.isEmpty() && binding.searchEditText.text.isEmpty()) {
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
    private fun hideLoading() {
        binding.loadingInfo.root.visibility = View.GONE
    }
    private fun showLoading() {
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

    override fun deleteById(position: Int) {
        TODO("Not yet implemented")
    }

    private fun initializeFoodRecyclerView() {
        foodAdapter = FoodAdapter(object: FoodAdapter.FoodClickable{
            override fun onFoodClick(foodItem: Food ) {
                val args = SearchFragmentDirections.actionSearchFragment2ToAddingSearchedProductFragment(foodItem, arg.mealId)
                Log.e("BOOBAA", foodItem.toString())
                findNavController().navigate(args)
            }
        }, arg.mealId)
        with(binding.foodRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = foodAdapter
        }
    }

    private fun initializeSearchHistoryRecyclerView() {
        searchHistoryAdapter =
            SearchHistoryAdapter(searchViewModel.searchHistoryManager.getSearchHistory(), this)
        showSearchHistory()
        binding.searchHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchHistoryAdapter
        }
    }
}