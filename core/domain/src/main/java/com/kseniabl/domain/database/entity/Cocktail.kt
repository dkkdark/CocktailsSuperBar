package com.kseniabl.domain.database.entity

data class Cocktail (
    val name: String = "",
    val description: String = "",
    val recipe: String = "",
    val ingredients: List<String> = emptyList()
)