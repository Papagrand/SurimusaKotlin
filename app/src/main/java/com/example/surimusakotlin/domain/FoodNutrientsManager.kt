package com.example.surimusakotlin.domain

class FoodNutrientsManager {
    var foodName: String = ""
    var calories: Double = 0.0
    var cholesterol: Double = 0.0
    var dietaryFiber: Double = 0.0
    var potassium: Double = 0.0
    var protein: Double = 0.0
    var saturated_fat: Double = 0.0
    var sodium: Double = 0.0
    var sugars: Double = 0.0
    var totalCarbohydrate: Double = 0.0
    var totalFat: Double = 0.0
    var grams: Double = 0.0

    fun updateValues(
        foodName: String,
        calories: Double,
        cholesterol: Double,
        dietaryFiber: Double,
        potassium: Double,
        protein: Double,
        fat: Double,
        sodium: Double,
        sugars: Double,
        totalCarbohydrate: Double,
        totalFat: Double,
        grams: Double,
    ) {
        this.foodName = foodName
        this.calories = calories
        this.cholesterol = cholesterol
        this.dietaryFiber = dietaryFiber
        this.potassium = potassium
        this.protein = protein
        this.saturated_fat = fat
        this.sodium = sodium
        this.sugars = sugars
        this.totalCarbohydrate = totalCarbohydrate
        this.totalFat = totalFat
        this.grams = grams
    }
}
