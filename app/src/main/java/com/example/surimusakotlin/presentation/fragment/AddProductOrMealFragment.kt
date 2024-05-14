package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.surimusakotlin.presentation.MainActivity
import com.example.surimusakotlin.R
import com.example.surimusakotlin.data.database.MainDB
import com.example.surimusakotlin.data.repository.EatingRepository
import com.example.surimusakotlin.databinding.FragmentAddProductOrMealBinding
import com.example.surimusakotlin.domain.usecase.addProduct.MaintainEatingRecordsUseCase
import com.example.surimusakotlin.domain.viewModels.AddProductOrMealViewModel
import com.example.surimusakotlin.domain.viewModels.AddProductOrMealViewModelFactory

class AddProductOrMealFragment : Fragment() {
    private lateinit var binding: FragmentAddProductOrMealBinding
    private val arg by navArgs<AddProductOrMealFragmentArgs>()
    private val totalNutritionDao by lazy {
        MainDB.getDB(requireContext()).totalNutritionDao()
    }
    private val viewModel: AddProductOrMealViewModel by viewModels{
        AddProductOrMealViewModelFactory(
            MaintainEatingRecordsUseCase(EatingRepository(totalNutritionDao))
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductOrMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.maintainRecordsAddProduct()

        (activity as? MainActivity)?.let {
            it.binding.bottomNavigation.visibility = View.GONE
        }

        binding.breakfastLunchDinner.text = arg.meal

        binding.searchViewButton.isEnabled = true
        binding.searchViewButton.setOnClickListener {
            findNavController().navigate(R.id.action_addProductOrMealFragment2_to_searchFragment2)
        }

        binding.backToProgress.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}