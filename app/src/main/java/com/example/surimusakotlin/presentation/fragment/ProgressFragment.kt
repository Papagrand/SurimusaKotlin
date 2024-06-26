package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.surimusakotlin.R
import com.example.surimusakotlin.data.database.Entities.Eating
import com.example.surimusakotlin.data.database.MainDB
import com.example.surimusakotlin.data.repository.EatingRepository
import com.example.surimusakotlin.data.repository.TotalNutritionRepository
import com.example.surimusakotlin.databinding.ProgressFragmentBinding
import com.example.surimusakotlin.domain.usecase.progress.GetEatingDataUseCase
import com.example.surimusakotlin.domain.usecase.progress.GetTotalNutritionUseCase
import com.example.surimusakotlin.domain.usecase.progress.MaintainTotalNutritionRecordsUseCase
import com.example.surimusakotlin.domain.usecase.progress.UpdateDayWaterUsecase
import com.example.surimusakotlin.domain.usecase.progress.UpdateTotalDayNutrientsUseCase
import com.example.surimusakotlin.domain.viewModels.ProgressViewModel
import com.example.surimusakotlin.domain.viewModels.factories.ProgressViewModelFactory
import com.example.surimusakotlin.presentation.MainActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.format
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
            GetEatingDataUseCase(EatingRepository(totalNutritionDao)),
            UpdateTotalDayNutrientsUseCase(totalNutritionDao),
            UpdateDayWaterUsecase(totalNutritionDao)

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
        viewModel.maintainRecordsProgress()
        //Временная логика, так как не сделан календарь
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date()).toLong()
        viewModel.loadNutritionData(currentDate)
        binding.numberWeek.text = getWeekOfYear(currentDate.toString()).toString()
        val currentDateBreakfast = currentDate*10+1
        val currentDateLunch = currentDate*10+2
        val currentDateDinner = currentDate*10+3
        val currentDateSnack = currentDate*10+4

        viewModel.getEatingData(currentDateBreakfast, "breakfast")
        viewModel.getEatingData(currentDateLunch, "lunch")
        viewModel.getEatingData(currentDateDinner, "dinner")
        viewModel.getEatingData(currentDateSnack, "snack")


        viewModel.breakfastData.observeOnce(viewLifecycleOwner) { eating ->
            if (eating != null) {
                updateMealUI(binding.breakfastDishesText, binding.circularProgressBarBreakfast, binding.breakfastCaloriesText, binding.maxBreakfastCaloriesText, eating, 576F, currentDate)
            }
        }

        viewModel.lunchData.observeOnce(viewLifecycleOwner) { eating ->
            if (eating != null) {
                updateMealUI(binding.lunchDishesText, binding.circularProgressBarLunch, binding.lunchCaloriesText, binding.maxLunchCaloriesText, eating, 765F, currentDate)
            }
        }

        viewModel.dinnerData.observeOnce(viewLifecycleOwner) { eating ->
            if (eating != null) {
                updateMealUI(binding.dinnerDishesText, binding.circularProgressBarDinner, binding.dinnerCaloriesText, binding.maxDinnerCaloriesText, eating, 482F, currentDate)
            }

        }

        viewModel.snackData.observeOnce(viewLifecycleOwner) { eating ->
            if (eating != null) {
                updateMealUI(binding.snackDishesText, binding.circularProgressBarSnack, binding.snackCaloriesText, binding.maxSnackCaloriesText, eating, 110F, currentDate)
            }
        }
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.let {
            it.binding.bottomNavigation.visibility = View.VISIBLE
        }

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

        binding.plusWater.setOnClickListener {
            viewModel.updateDayWater(
                0.25,
                currentDate
            )
        }
        binding.minusWater.setOnClickListener {
            viewModel.updateDayWater(
                -0.25,
                currentDate
            )
        }
        //TODO "Сделать нормальное получение воды"
        viewModel.totalWater.observe(viewLifecycleOwner){
            val water = it
            binding.waterValue.text = water.toString() + " литров"

        }

        viewModel.totalCalories.observe(viewLifecycleOwner){
            val totalCalories = it
            val newLastCaloriesValue = 1919 - totalCalories
            binding.eatedCalories.text = "%.0f".format(totalCalories)
            binding.lastCalories.text = "%.0f".format(newLastCaloriesValue)
            binding.circularProgressBarCalories.progress = it.toFloat()

        }
        viewModel.totalDayCarbohydrate.observe(viewLifecycleOwner){
            val totalCarbohydrate = it
            val maxCarbohydrate = 234
            binding.carbonsGrams.text = "%.0f".format(totalCarbohydrate) + " / " + maxCarbohydrate.toString() + "г"
            binding.carbonsProgressBar.max = maxCarbohydrate
            binding.carbonsProgressBar.progress = totalCarbohydrate.toInt()

        }
        viewModel.totalDayFat.observe(viewLifecycleOwner){
            val totalFat = it
            val maxFat = 62
            binding.fatsGrams.text = "%.0f".format(totalFat) + " / " + maxFat.toString() + "г"
            binding.fatsProgressBar.max = maxFat
            binding.fatsProgressBar.progress = totalFat.toInt()
        }
        viewModel.totalDayProtein.observe(viewLifecycleOwner){
            val totalProtein = it
            val maxProtein = 62
            binding.proteinsGrams.text = "%.0f".format(totalProtein) + " / " + maxProtein.toString() + "г"
            binding.proteinsProgressBar.max = maxProtein
            binding.proteinsProgressBar.progress = totalProtein.toInt()
        }



    }

    override fun onStop() {
        super.onStop()
        viewModel.setDefaultNutrients(
            0.0,
            0.0,
            0.0,
            0.0,
            0.0)

    }

    fun getWeekOfYear(dateStr: String): Int {
        val dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        val date = dateFormat.parse(dateStr) ?: return -1 // Возвращает -1, если дата неправильная

        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.minimalDaysInFirstWeek = 7

        return calendar.get(Calendar.WEEK_OF_YEAR)
    }

    private fun updateMealUI(dishesText: TextView, progressBar: CircularProgressBar, caloriesText: TextView, maxCaloriesText: TextView, eating: Eating, maxCalories: Float, currentDate: Long) {
        dishesText.text = eating.nameProducts
        progressBar.progressMax = maxCalories
        val currentCalories = "%.0f".format(eating.totalCaloriesEating) + " / "
        caloriesText.text = currentCalories
        maxCaloriesText.text = "%.0f".format(maxCalories) + " ккал"
        progressBar.progress = eating.totalCaloriesEating!!.toFloat()


        viewModel.updateTotalDayNutrients(
            eating.totalCaloriesEating ?: 0.0,
            eating.totalProteinsEating?: 0.0,
            eating.totalCarbohydrateEating?: 0.0,
            eating.totalFatsEating?: 0.0,
            currentDate
        )
    }
    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }


}