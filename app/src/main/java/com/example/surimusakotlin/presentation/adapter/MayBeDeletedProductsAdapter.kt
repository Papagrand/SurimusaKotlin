package com.example.surimusakotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surimusakotlin.R
import com.example.surimusakotlin.data.database.Entities.Product
import com.example.surimusakotlin.domain.viewModels.BottomSheetDeletingViewModel

class MayBeDeletedProductsAdapter(private val foodClickable: FoodAdapter.FoodClickable): RecyclerView.Adapter<MayBeDeletedProductsAdapter.MayBeDeletedProductsViewHolder>() {
    var products: List<Product> = emptyList()
    class MayBeDeletedProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MayBeDeletedProductsAdapter.MayBeDeletedProductsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_may_be_deleted, parent, false)
        return MayBeDeletedProductsViewHolder(itemView)
    }


    override fun onBindViewHolder(
        holder: MayBeDeletedProductsAdapter.MayBeDeletedProductsViewHolder,
        position: Int
    ) {
        val currentFood = products[position]
        val calories = String.format("%.1f", currentFood?.calories ?: 0) + " ккал"
        val carbohydrates = String.format("%.1f", currentFood?.totalCarbohydrate ?: 0) + " гр."
        val proteins = String.format("%.1f", currentFood?.protein ?: 0) + " гр."
        val fats = String.format("%.1f", currentFood?.totalFat ?: 0) + " гр"
        val servingWeight = String.format("%.1f", currentFood?.grams ?: 0) + " гр."

        holder.itemView.findViewById<TextView>(R.id.food_text_recycler).text = currentFood?.productName ?: "Нет данных"
        holder.itemView.findViewById<TextView>(R.id.calories_of_product).text = calories
        holder.itemView.findViewById<TextView>(R.id.carbons_recycler_grams).text = carbohydrates
        holder.itemView.findViewById<TextView>(R.id.proteins_recycler_grams).text = proteins
        holder.itemView.findViewById<TextView>(R.id.fats_recycler_grams).text = fats
        holder.itemView.findViewById<TextView>(R.id.mass_of_product).text = servingWeight

        holder.itemView.findViewById<ImageButton>(R.id.button_delete_food).setOnClickListener {
            foodClickable.onDeleteClick(currentFood)
        }
    }

    override fun getItemCount() = products.size

}