package com.example.surimusakotlin.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.surimusakotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    public lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        NavigationUI.setupWithNavController(
            navigationBarView = binding.bottomNavigation,
            navController = Navigation.findNavController(this, binding.frame.id)
        )

    }

}