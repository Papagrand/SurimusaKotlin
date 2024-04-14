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
        holder.itemView.findViewById<TextView>(R.id.food_text_recycler).text = foodInfo.food_name
        holder.itemView.findViewById<TextView>(R.id.calories_of_product).text = foodInfo.serving_weight_grams.toString()+" гр"
        holder.itemView.findViewById<TextView>(R.id.carbons_recycler_grams).text = foodInfo.nf_total_carbohydrate.toString()+" гр."
        holder.itemView.findViewById<TextView>(R.id.proteins_recycler_grams).text = foodInfo.nf_protein.toString()+" гр."
        holder.itemView.findViewById<TextView>(R.id.fats_recycler_grams).text = foodInfo.nf_total_fat.toString()+" гр"
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