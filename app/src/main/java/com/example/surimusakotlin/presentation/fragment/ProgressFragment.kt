package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.MainDB
import com.example.surimusakotlin.data.repository.EatingRepository
import com.example.surimusakotlin.data.repository.TotalNutritionRepository
import com.example.surimusakotlin.databinding.ProgressFragmentBinding
import com.example.surimusakotlin.domain.usecase.progress.GetEatingDataUseCase
import com.example.surimusakotlin.domain.usecase.progress.GetTotalNutritionUseCase
import com.example.surimusakotlin.domain.usecase.progress.MaintainTotalNutritionRecordsUseCase
import com.example.surimusakotlin.domain.viewModels.ProgressViewModel
import com.example.surimusakotlin.domain.viewModels.factories.ProgressViewModelFactory
import com.example.surimusakotlin.presentation.MainActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ProgressFragment : Fragment() {
    private lateinit var binding: ProgressFragmentBinding
    private val totalNutritionDao by lazy {
        MainDB.getDB(requireContext()).totalNutritionDao()
    }
    private val viewModel: ProgressViewModel by viewModels {
        ProgressViewModelFactory(
            GetTotalNutritionUseCase(TotalNutritionRepository(totalNutritionDao)),
            MaintainTotalNutritionRecordsUseCase(TotalNutritionRepository(totalNutritionDao)),
            GetEatingDataUseCase(EatingRepository(totalNutritionDao))
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProgressFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.let {
            it.binding.bottomNavigation.visibility = View.VISIBLE
        }

        viewModel.maintainRecordsProgress()
        //Временная логика, так как не сделан календарь
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date()).toLong()
        viewModel.loadNutritionData(currentDate)
        binding.numberWeek.text = getWeekOfYear(currentDate.toString()).toString()
        val currentDateBreakfast = currentDate*10+1
        val currentDateLunch = currentDate*10+2
        val currentDateDinner = currentDate*10+3
        val currentDateSnack = currentDate*10+4

        binding.addBreakfastDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2( currentDateBreakfast)
            findNavController().navigate(destination)
        }
        binding.addLunchDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2( currentDateLunch)
            findNavController().navigate(destination)
        }
        binding.addDinnerDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2( currentDateDinner)
            findNavController().navigate(destination)
        }
        binding.addSnackDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2( currentDateSnack)
            findNavController().navigate(destination)
        }

        viewModel.getEatingData(currentDateBreakfast, "breakfast")
        viewModel.getEatingData(currentDateLunch, "lunch")
        viewModel.getEatingData(currentDateDinner, "dinner")
        viewModel.getEatingData(currentDateSnack, "snack")

        viewModel.breakfastData.observe(viewLifecycleOwner) { eating ->
            if (eating != null) {
                updateMealUI(binding.breakfastDishesText, binding.circularProgressBarBreakfast, binding.breakfastCaloriesText, binding.maxBreakfastCaloriesText, eating, 576F)
            }
        }

        viewModel.lunchData.observe(viewLifecycleOwner) { eating ->
            if (eating != null) {
                updateMealUI(binding.lunchDishesText, binding.circularProgressBarLunch, binding.lunchCaloriesText, binding.maxLunchCaloriesText, eating, 765F)
            }
        }

        viewModel.dinnerData.observe(viewLifecycleOwner) { eating ->
            if (eating != null) {
                updateMealUI(binding.dinnerDishesText, binding.circularProgressBarDinner, binding.dinnerCaloriesText, binding.maxDinnerCaloriesText, eating, 482F)
            }
        }

        viewModel.snackData.observe(viewLifecycleOwner) { eating ->
            if (eating != null) {
                updateMealUI(binding.snackDishesText, binding.circularProgressBarSnack, binding.snackCaloriesText, binding.maxSnackCaloriesText, eating, 110F)
            }
        }

    }

    fun getWeekOfYear(dateStr: String): Int {
        val dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        val date = dateFormat.parse(dateStr) ?: return -1 // Возвращает -1, если дата неправильная

        val calendar = Calendar.getInstance()
        calendar.time = date

        // Установка первого дня недели как воскресенье и минимального числа дней в первой неделе года как 1
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.minimalDaysInFirstWeek = 7

        return calendar.get(Calendar.WEEK_OF_YEAR)
    }

    private fun updateMealUI(dishesText: TextView, progressBar: CircularProgressBar, caloriesText: TextView, maxCaloriesText: TextView, eating: Eating, maxCalories: Float) {
        dishesText.text = eating.nameProducts
        progressBar.progressMax = maxCalories
        val currentCalories = "%.0f".format(eating.totalCaloriesEating) + " / "
        caloriesText.text = currentCalories
        maxCaloriesText.text = "%.0f".format(maxCalories) + " ккал"
        progressBar.progress = eating.totalCaloriesEating!!.toFloat()
    }

}