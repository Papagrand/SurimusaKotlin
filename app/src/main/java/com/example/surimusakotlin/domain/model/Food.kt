package com.example.surimusakotlin.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val food_name: String,
    val nf_calories: Double,
    val nf_cholesterol: Double,//холестерин
    val nf_dietary_fiber: Double,//Пищевая клетчатка
    val nf_potassium: Double,//калий
    val nf_protein: Double,//белок
    val nf_saturated_fat: Double,//Насыщенные жиры
    val nf_sodium: Double,//натрий
    val nf_sugars: Double,//Сахар
    val nf_total_carbohydrate: Double,
    val nf_total_fat: Double,//Общие жиры
    val photo: Photo,
    val serving_weight_grams: Double,
): Parcelable