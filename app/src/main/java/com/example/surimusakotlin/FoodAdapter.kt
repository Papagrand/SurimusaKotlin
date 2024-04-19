package com.example.surimusakotlin

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surimusakotlin.data.repository.FoodRepository
import com.example.surimusakotlin.model.Branded
import com.example.surimusakotlin.model.Common
import com.example.surimusakotlin.model.Food
import com.example.surimusakotlin.model.FoodInstant
import com.example.surimusakotlin.model.Nutrition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    var listFood = emptyList<Food>()

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_food, parent, false)
        return FoodViewHolder(view);
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodInfo = listFood[position]
        val calories = String.format("%.1f", foodInfo.nf_calories ?: 0) + " ккал"
        val carbohydrates = String.format("%.1f", foodInfo.nf_total_carbohydrate ?: 0) + " гр."
        val proteins = String.format("%.1f", foodInfo.nf_protein ?: 0) + " гр."
        val fats = String.format("%.1f", foodInfo.nf_total_fat ?: 0) + " гр"
        val servingWeight = String.format("%.1f", foodInfo.serving_weight_grams ?: 0) + " гр."

        holder.itemView.findViewById<TextView>(R.id.food_text_recycler).text = foodInfo.food_name ?: "Нет данных"
        holder.itemView.findViewById<TextView>(R.id.calories_of_product).text = calories
        holder.itemView.findViewById<TextView>(R.id.carbons_recycler_grams).text = carbohydrates
        holder.itemView.findViewById<TextView>(R.id.proteins_recycler_grams).text = proteins
        holder.itemView.findViewById<TextView>(R.id.fats_recycler_grams).text = fats
        holder.itemView.findViewById<TextView>(R.id.mass_of_product).text = servingWeight


    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Food>){
        listFood = list
        notifyDataSetChanged()
    }

    fun clearList() {
        listFood = emptyList()
        notifyDataSetChanged()
    }

}