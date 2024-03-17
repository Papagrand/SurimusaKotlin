package com.example.surimusakotlin

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FourthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_or_meal)
        val meal = intent.getStringExtra("meal")
        val breakfastLunchDinnerTextView = findViewById<TextView>(R.id.breakfast_lunch_dinner)
        breakfastLunchDinnerTextView.text = meal
    }
}