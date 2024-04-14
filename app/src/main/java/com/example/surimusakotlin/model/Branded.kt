package com.example.surimusakotlin.model

data class Branded(
    val brand_name: String,
    val brand_name_item_name: String,
    val brand_type: Int,
    val food_name: String,
    val locale: String,
    val nf_calories: Int,
    val nix_brand_id: String,
    val nix_item_id: String,
    val region: Int,
    val serving_qty: Double,
    val serving_unit: String
)