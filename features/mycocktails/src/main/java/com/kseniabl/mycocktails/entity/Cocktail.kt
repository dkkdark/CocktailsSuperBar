package com.kseniabl.mycocktails.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Cocktail (
    val name: String = "",
    val description: String = "",
    val recipe: String = "",
    val ingredients: List<String> = emptyList(),
    // hardcode image
    val image: Int
): Parcelable