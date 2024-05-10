package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.surimusakotlin.databinding.ProgressFragmentBinding
import com.example.surimusakotlin.presentation.MainActivity

class ProgressFragment : Fragment() {
    private lateinit var binding: ProgressFragmentBinding


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

        binding.addBreakfastDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2(binding.breakfastText.text.toString())
            findNavController().navigate(destination)
        }
        binding.addLunchDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2(binding.lunchText.text.toString())
            findNavController().navigate(destination)
        }
        binding.addDinnerDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2(binding.dinnerText.text.toString())
            findNavController().navigate(destination)
        }
        binding.addSnackDishButton.setOnClickListener{
            val destination = ProgressFragmentDirections.actionProgressFragmentToAddProductOrMealFragment2(binding.snackText.text.toString())
            findNavController().navigate(destination)
        }






    }
}