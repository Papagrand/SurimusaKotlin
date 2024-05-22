package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.data.database.MainDB
import com.example.surimusakotlin.data.repository.ProductRepository
import com.example.surimusakotlin.databinding.FragmentBottomSheetDeletingBinding
import com.example.surimusakotlin.domain.model.Food
import com.example.surimusakotlin.domain.usecase.bottomSheet.DeleteProductUseCase
import com.example.surimusakotlin.domain.usecase.bottomSheet.GetProductsInAddProductUseCase
import com.example.surimusakotlin.domain.viewModels.BottomSheetDeletingViewModel
import com.example.surimusakotlin.domain.viewModels.factories.BottomSheetDeletingViewModelFactory
import com.example.surimusakotlin.presentation.adapter.FoodAdapter
import com.example.surimusakotlin.presentation.adapter.MayBeDeletedProductsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDeletingFragment : BottomSheetDialogFragment(), FoodAdapter.FoodClickable {

    private lateinit var binding: FragmentBottomSheetDeletingBinding
    private val mayBeDeletedProductsAdapter = MayBeDeletedProductsAdapter(this)
    private val arg by navArgs<BottomSheetDeletingFragmentArgs>()
    private val totalNutritionDao by lazy {
        MainDB.getDB(requireContext()).totalNutritionDao()
    }
    private val viewModel: BottomSheetDeletingViewModel by viewModels{
        BottomSheetDeletingViewModelFactory(
            GetProductsInAddProductUseCase(ProductRepository(totalNutritionDao)),
            DeleteProductUseCase(ProductRepository(totalNutritionDao))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetDeletingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductsFromThisEating(arg.mealId)
        viewModel.productData.observe(viewLifecycleOwner){listProduct ->
            mayBeDeletedProductsAdapter.products = listProduct
            with(binding.foodMayBeDeletedRecyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mayBeDeletedProductsAdapter
            }
        }

        binding.closeText.setOnClickListener{
            dismiss()
        }

    }

    override fun onFoodClick(foodItem: Food) {
        TODO("Not yet implemented")
    }

    override fun onDeleteClick(product: Product) {
        viewModel.deleteProduct(product.id)
    }





}