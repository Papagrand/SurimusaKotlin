package com.example.surimusakotlin.adapter_utils

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.surimusakotlin.model.Food

class DiffUtilCallback(
    private val old: List<Food>,
    private val new: List<Food>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].food_name == new[newItemPosition].food_name
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return old[oldItemPosition].food_name == new[newItemPosition].food_name
    }
}