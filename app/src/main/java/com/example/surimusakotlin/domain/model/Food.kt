package com.example.surimusakotlin.domain.model

data class Food(
    val alt_measures: List<AltMeasure>,
    val brand_name: Any,
    val brick_code: Any,
    val class_code: Any,
    val consumed_at: String,
    val food_name: String,
    val full_nutrients: List<FullNutrient>,
    val lat: Any,
    val lng: Any,
    val meal_type: Int,
    val metadata: Metadata,
    val ndb_no: Int,
    val nf_calories: Double,
    val nf_cholesterol: Double,//холестерин
    val nf_dietary_fiber: Double,//Пищевая клетчатка
    val nf_p: Double,
    val nf_potassium: Double,//калий
    val nf_protein: Double,//белок
    val nf_saturated_fat: Double,//Насыщенные жиры
    val nf_sodium: Double,//натрий
    val nf_sugars: Double,//Сахар
    val nf_total_carbohydrate: Double,
    val nf_total_fat: Double,//Общие жиры
    val nix_brand_id: Any,
    val nix_brand_name: Any,
    val nix_item_id: Any,
    val nix_item_name: Any,
    val photo: Photo,
    val serving_qty: Double,
    val serving_unit: String,
    val serving_weight_grams: Double,
    val source: Int,
    val sub_recipe: Any,
    val tag_id: Any,
    val tags: Tags,
    val upc: Any
)