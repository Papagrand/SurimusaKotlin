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
import com.example.surimusakotlin.model.Food
import com.example.surimusakotlin.model.FoodInformation
import com.example.surimusakotlin.model.FoodInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    var listFood = emptyList<Branded>()

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_food, parent, false)
        return FoodViewHolder(view);
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodInfo = listFood[position]
        holder.itemView.findViewById<TextView>(R.id.food_text_recycler).text = foodInfo.brand_name_item_name
        holder.itemView.findViewById<TextView>(R.id.calories_of_product).text = foodInfo.nf_calories.toString()+" ккал"


        GlobalScope.launch(Dispatchers.IO) {
            try {
                val additionalInfoResponse: Response<Food> = FoodRepository().getAdditionalFoodInfo(foodInfo.nix_item_id)
                withContext(Dispatchers.Main) {
                    if (additionalInfoResponse.isSuccessful) {
                        val additionalInfo:Food? = additionalInfoResponse.body()
                        if (additionalInfo != null && !additionalInfo.foods.isNullOrEmpty()) {
//                            holder.itemView.findViewById<TextView>(R.id.mass_of_product).text = additionalInfo.foods[position].serving_weight_grams.toString()+" гр."
//                            holder.itemView.findViewById<TextView>(R.id.carbons_recycler_grams).text = additionalInfo.foods[position].nf_total_carbohydrate.toString()+" гр."
//                            holder.itemView.findViewById<TextView>(R.id.proteins_recycler_grams).text = additionalInfo.foods[position].nf_protein.toString()+" гр."
//                            holder.itemView.findViewById<TextView>(R.id.fats_recycler_grams).text = additionalInfo.foods[position].nf_total_fat.toString()+" гр."
                        } else {
                            // Обработка случая, когда данные не получены
                        }
                    } else {
                        Log.e("FoodAdapter", "Failed to fetch additional food info: ${additionalInfoResponse.message()}")
                        // Обработка случая, когда запрос завершился неудачно
                    }
                }
            } catch (e: Exception) {
                Log.e("FoodAdapter", "Error fetching additional food info: ${e.message}")
                // Обработка ошибки
            }
        }
    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Branded>){
        listFood = list
        notifyDataSetChanged()
    }

    fun clearList() {
        listFood = emptyList()
        notifyDataSetChanged()
    }

}