package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.surimusakotlin.R
import com.example.surimusakotlin.databinding.FragmentAddingSearchedProductBinding
import com.example.surimusakotlin.domain.FoodNutrientsManager

class AddingSearchedProductFragment : Fragment() {
    private val args: AddingSearchedProductFragmentArgs by navArgs()

    private lateinit var binding: FragmentAddingSearchedProductBinding
    private val foodNutrientsManager = FoodNutrientsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddingSearchedProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodNutrientsManager.updateValues(
            args.foodItem.food_name,
            args.foodItem.nf_calories,
            args.foodItem.nf_cholesterol,
            args.foodItem.nf_dietary_fiber,
            args.foodItem.nf_potassium,
            args.foodItem.nf_protein,
            args.foodItem.nf_saturated_fat,
            args.foodItem.nf_sodium,
            args.foodItem.nf_sugars,
            args.foodItem.nf_total_carbohydrate,
            args.foodItem.nf_total_fat,
            args.foodItem.serving_weight_grams
        )

        val pic: String = args.foodItem.photo.highres ?: args.foodItem.photo.thumb ?: resources.getString(R.string.link_to_image)
        Glide.with(this).load(pic).into(binding.imageOfProduct)
        binding.editTextForGrams.setText("%.2f".format(foodNutrientsManager.grams))

        updateTextViews()

        binding.editTextForGrams.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                updateNutrientValues(s)
                updateTextViews()
            }
        })
    }

    private fun updateNutrientValues(s: Editable?) {
        val grams = foodNutrientsManager.grams
        var newGrams = s.toString().toDoubleOrNull()
        if (newGrams==null){
            newGrams = 1.0
        }
        with(foodNutrientsManager){
            calories=calories/grams*newGrams
            totalFat=totalFat/grams*newGrams
            saturated_fat=saturated_fat/grams*newGrams
            cholesterol=cholesterol/grams*newGrams
            sodium=sodium/grams*newGrams
            totalCarbohydrate=totalCarbohydrate/grams*newGrams
            dietaryFiber=dietaryFiber/grams*newGrams
            sugars=sugars/grams*newGrams
            protein=protein/grams*newGrams
            potassium=potassium/grams*newGrams
        }
        foodNutrientsManager.grams = newGrams


    }
    private fun updateTextViews() {
        with(binding){
            productText.text = foodNutrientsManager.foodName
            editTextForCalories.setText("%.2f".format(foodNutrientsManager.calories ?: 0))
            val grams = foodNutrientsManager.grams ?: 0.0
            val nutrientsString = getString(R.string.final_nutrient_in_product, "%.2f".format(grams))
            finalNutrientsText.text = nutrientsString
            totalFatTextView.text = getString(R.string.total_fat)+" "+"%.2f".format(foodNutrientsManager.totalFat ?: 0)+" гр"
            saturatedFatTextView.text = getString(R.string.saturated_fat)+" "+"%.2f".format(foodNutrientsManager.saturated_fat ?: 0)+" гр"
            cholesterolTextView.text = getString(R.string.cholesterol)+" "+"%.2f".format(foodNutrientsManager.cholesterol ?: 0)+" гр"
            sodiumTextView.text = getString(R.string.sodium)+" "+"%.2f".format(foodNutrientsManager.sodium ?: 0)+" мг"
            totalCarbohydratesTextView.text = getString(R.string.total_carbohydrates)+" "+"%.2f".format(foodNutrientsManager.totalCarbohydrate ?: 0)+" гр"
            dietaryFiberTextView.text = getString(R.string.dietary_fiber)+" "+"%.2f".format(foodNutrientsManager.dietaryFiber ?: 0)+" гр"
            sugarTextView.text = getString(R.string.sugar)+" "+"%.2f".format(foodNutrientsManager.sugars ?: 0)+" гр"
            proteinsTextView.text = getString(R.string.proteins)+" "+"%.2f".format(foodNutrientsManager.protein ?: 0)+" гр"
            potassiumTextView.text = getString(R.string.potassium)+" "+"%.2f".format(foodNutrientsManager.potassium ?: 0)+" мг"
        }
    }

}