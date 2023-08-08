package com.kseniabl.mycocktails.entity


data class Cocktail (
    val name: String = "",
    val description: String = "",
    val recipe: String = "",
    val ingredients: List<String> = emptyList(),
    // hardcode image
    val image: Int
)