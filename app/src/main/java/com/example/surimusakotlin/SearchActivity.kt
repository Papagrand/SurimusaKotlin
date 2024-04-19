package com.example.surimusakotlin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.surimusakotlin.data.ScreenSwitchable
import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.databinding.ActivitySearchBinding
import com.example.surimusakotlin.model.FoodInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchActivity : AppCompatActivity(), ScreenSwitchable {
    private val foodRepository = FoodRepository()
    private val searchViewModel = SearchViewModel(foodRepository, this)

    private var searchText: String? = null
    private val foodAdapter = FoodAdapter()
    private var searchJob: Job? = null

    private lateinit var binding: ActivitySearchBinding

    private lateinit var searchEditText: EditText // Изменено на EditText

    private val searchScope = lifecycleScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchEditText = binding.searchEditText // Изменено на EditText

        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString("searchText")
            searchEditText.setText(searchText) // Установка сохраненного текста
        }

        initializeRecyclerView()

        val backButton = binding.backToSearchMeal
        backButton.setOnClickListener {
            val intent = Intent(this@SearchActivity, FourthActivity::class.java)
            startActivity(intent)
            finish()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                searchText = s.toString()
                searchJob?.cancel()
                if (!s.isNullOrEmpty()) {
                    showDeleteCross()
                    searchJob = searchScope.launch(Dispatchers.Main) {
                        delay(1000)
                        searchViewModel.makeRequest(s.toString())
                    }
                }else{
                    hideDeleteCross()
                    foodAdapter.clearList()
                }
            }
        }

        binding.clearButton.setOnClickListener {
            searchEditText.text.clear();
        }

        searchEditText.addTextChangedListener(textWatcher)

        lifecycleScope.launch {
            searchViewModel.foodInstantList.collectLatest {
                foodAdapter.clearList()
                foodAdapter.setList(it)
                foodAdapter.notifyDataSetChanged()
            }
        }
    }

    fun onClickReload(view: View) {
        val searchText = searchEditText.text.toString()
        searchJob?.cancel()
        if (searchText.isNotEmpty()) {
            searchJob = searchScope.launch(Dispatchers.Main) {
                delay(1000)
                searchViewModel.makeRequest(searchText)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchText", searchText)
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

    private fun initializeRecyclerView() {
        with(binding.foodRecyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = foodAdapter
        }
    }
}
