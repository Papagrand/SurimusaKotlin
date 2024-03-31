package com.example.surimusakotlin

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.SearchView
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


        val searchView = findViewById<ImageButton>(R.id.search_view_button)
        searchView.isEnabled = true;

        searchView.setOnClickListener{
            val intent = Intent(this@FourthActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        val backButton = findViewById<ImageButton>(R.id.back_to_progress)
        backButton.setOnClickListener{
            val intent = Intent(this@FourthActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}
