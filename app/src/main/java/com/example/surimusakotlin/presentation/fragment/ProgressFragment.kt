package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val curentDateBreakfast = currentDate*10+1
        val curentDateLunch = currentDate*10+2
        val curentDateDinner = currentDate*10+3
        val curentDateSnack = currentDate*10+4

        binding.addBreakfastDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2( curentDateBreakfast)
            findNavController().navigate(destination)
        }
        binding.addLunchDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2( curentDateLunch)
            findNavController().navigate(destination)
        }
        binding.addDinnerDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2( curentDateDinner)
            findNavController().navigate(destination)
        }
        binding.addSnackDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2( curentDateSnack)
            findNavController().navigate(destination)
        }

        viewModel.getEatingData(curentDateBreakfast){eating ->
            binding.breakfastDishesText.text = eating?.nameProducts
            binding.circularProgressBarBreakfast.progressMax = 576F
            binding.breakfastCaloriesText.text = "%.0f".format(eating?.totalCaloriesEating)+" / "
            binding.maxBreakfastCaloriesText.text = "%.0f".format(binding.circularProgressBarBreakfast.progressMax) + " ккал"
            binding.circularProgressBarBreakfast.progress = eating?.totalCaloriesEating!!.toFloat()
        }
        viewModel.getEatingData(curentDateLunch){eating ->
            binding.lunchDishesText.text = eating?.nameProducts
            binding.circularProgressBarLunch.progressMax = 765F
            binding.lunchCaloriesText.text = "%.0f".format(eating?.totalCaloriesEating)+" / "
            binding.maxLunchCaloriesText.text = "%.0f".format(binding.circularProgressBarLunch.progressMax) + " ккал"
            binding.circularProgressBarLunch.progress = eating?.totalCaloriesEating!!.toFloat()
        }
        viewModel.getEatingData(curentDateDinner){eating ->
            binding.dinnerDishesText.text = eating?.nameProducts
            binding.circularProgressBarDinner.progressMax = 482F
            binding.dinnerCaloriesText.text = "%.0f".format(eating?.totalCaloriesEating)+" / "
            binding.maxDinnerCaloriesText.text = "%.0f".format(binding.circularProgressBarDinner.progressMax) + " ккал"
            binding.circularProgressBarDinner.progress = eating?.totalCaloriesEating!!.toFloat()
        }
        viewModel.getEatingData(curentDateSnack){eating ->
            binding.snackDishesText.text = eating?.nameProducts
            binding.circularProgressBarSnack.progressMax = 110F
            binding.snackCaloriesText.text = "%.0f".format(eating?.totalCaloriesEating)+" / "
            binding.maxSnackCaloriesText.text = "%.0f".format(binding.circularProgressBarSnack.progressMax) + " ккал"
            binding.circularProgressBarSnack.progress = eating?.totalCaloriesEating!!.toFloat()
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
}