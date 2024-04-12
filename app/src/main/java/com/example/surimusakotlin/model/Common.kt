package com.example.surimusakotlin.model

data class Common(
    val common_type: Any,
    val food_name: String,
    val locale: String,
    val photo: PhotoXX,
    val serving_qty: Double,
    val serving_unit: String,
    val tag_id: String,
    val tag_name: String
)