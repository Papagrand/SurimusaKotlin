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
    var servingQty: Double = 0.0
    var servingUnit: String = ""
    var grams: Double = 0.0
    var source: Int = 0

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
        servingQty: Double,
        servingUnit: String,
        grams: Double,
        source: Int
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
        this.servingQty = servingQty
        this.servingUnit = servingUnit
        this.grams = grams
        this.source = source
    }
}
