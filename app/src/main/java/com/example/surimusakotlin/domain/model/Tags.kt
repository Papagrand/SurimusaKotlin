package com.example.surimusakotlin.domain.model

data class Tags(
    val food_group: Int,
    val item: String,
    val measure: Any,
    val quantity: String,
    val tag_id: Int
)