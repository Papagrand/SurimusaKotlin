package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.surimusakotlin.presentation.MainActivity
import com.example.surimusakotlin.R
import com.example.surimusakotlin.data.database.MainDB
import com.example.surimusakotlin.data.repository.EatingRepository
import com.example.surimusakotlin.data.repository.ProductRepository
import com.example.surimusakotlin.databinding.FragmentAddProductOrMealBinding
import com.example.surimusakotlin.domain.usecase.addProduct.AddProductsDataUseCase
import com.example.surimusakotlin.domain.usecase.addProduct.MaintainEatingRecordsUseCase
import com.example.surimusakotlin.domain.usecase.bottomSheet.GetProductsInAddProductUseCase
import com.example.surimusakotlin.domain.usecase.progress.GetEatingDataUseCase
import com.example.surimusakotlin.domain.viewModels.AddProductOrMealViewModel
import com.example.surimusakotlin.domain.viewModels.factories.AddProductOrMealViewModelFactory

class AddProductOrMealFragment : Fragment() {
    private lateinit var binding: FragmentAddProductOrMealBinding
    private val arg by navArgs<AddProductOrMealFragmentArgs>()
    private val totalNutritionDao by lazy {
        MainDB.getDB(requireContext()).totalNutritionDao()
    }
    private val viewModel: AddProductOrMealViewModel by viewModels{
        AddProductOrMealViewModelFactory(
            MaintainEatingRecordsUseCase(EatingRepository(totalNutritionDao)),
            AddProductsDataUseCase(EatingRepository(totalNutritionDao)),
            GetEatingDataUseCase(EatingRepository(totalNutritionDao)),
            GetProductsInAddProductUseCase(ProductRepository(totalNutritionDao))
        )
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
        viewModel.maintainRecordsAddProduct()
        viewModel.getEatingCurrentData(arg.mealId)
        viewModel.getCountProducts(arg.mealId)
        viewModel.getAllProductsFromThisEating(arg.mealId)
        viewModel.productData.observe(viewLifecycleOwner) { eating ->
            if (eating != null) {
                binding.countOfAddedProducts.text = eating.size.toString()
                if (eating.size > 0){
                    binding.countOfProducts.setOnClickListener{
                        val destination = AddProductOrMealFragmentDirections.actionAddProductOrMealFragment2ToBottomSheetDeletingFragment(arg.mealId)
                        if ( binding.countOfAddedProducts.text != "0") {
                            findNavController().navigate(destination)
                        }
                    }
                }
            }

        }

        val mealText = when (arg.mealId%10) {
            1L -> "Завтрак"
            2L -> "Обед"
            3L -> "Ужин"
            4L -> "Перекус"
            else -> "Неизвестный прием пищи"
        }
        binding.breakfastLunchDinner.text = mealText

        binding.searchViewButton.isEnabled = true
        binding.searchViewButton.setOnClickListener {
            val destination = AddProductOrMealFragmentDirections.actionAddProductOrMealFragment2ToSearchFragment2(arg.mealId)
            findNavController().navigate(destination)
        }



        binding.backToProgress.setOnClickListener {
            findNavController().navigate(R.id.progressFragment)
        }
        binding.doneButton.setOnClickListener {
            findNavController().navigate(R.id.progressFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.progressFragment)
            }
        })
    }
}