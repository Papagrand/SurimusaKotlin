package com.example.surimusakotlin.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.surimusakotlin.MainActivity
import com.example.surimusakotlin.R
import com.example.surimusakotlin.databinding.FragmentAddProductOrMealBinding

class AddProductOrMealFragment : Fragment() {
    private lateinit var binding: FragmentAddProductOrMealBinding
    private val arg by navArgs<AddProductOrMealFragmentArgs>()


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