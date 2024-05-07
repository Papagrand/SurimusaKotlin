package com.example.surimusakotlin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.surimusakotlin.databinding.FragmentSearchBinding
import com.example.surimusakotlin.databinding.ProgressFragmentBinding
import com.example.surimusakotlin.search.AddingProductToMealActivity

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

        val button: Button = view.findViewById(R.id.add_breakfast_dish_button)


        button.setOnClickListener {
            val intent = Intent(requireActivity(), AddingProductToMealActivity::class.java)
            startActivity(intent)
        }


    }
}