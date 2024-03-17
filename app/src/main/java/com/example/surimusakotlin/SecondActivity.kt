package com.example.surimusakotlin


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progress_fragment)

        val addBreakfastDishButton1 = findViewById<Button>(R.id.add_breakfast_dish_button)
        addBreakfastDishButton1.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            intent.putExtra("meal", "Завтрак")
            startActivity(intent)
        }

        val addBreakfastDishButton2 = findViewById<Button>(R.id.add_lunch_dish_button)
        addBreakfastDishButton2.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            intent.putExtra("meal", "Обед")
            startActivity(intent)
        }
        val addBreakfastDishButton3 = findViewById<Button>(R.id.add_dinner_dish_button)
        addBreakfastDishButton3.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            intent.putExtra("meal", "Ужин")
            startActivity(intent)
        }
        val addBreakfastDishButton4 = findViewById<Button>(R.id.add_snack_dish_button)
        addBreakfastDishButton4.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            intent.putExtra("meal", "Перекус")
            startActivity(intent)
        }
    }
}
