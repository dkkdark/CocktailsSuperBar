package com.kseniabl.domain.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CocktailModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val recipe: String = "",
    val createdAt: Long = 0,
    val ingredients: List<String> = emptyList(),
    val image: String = ""
)
