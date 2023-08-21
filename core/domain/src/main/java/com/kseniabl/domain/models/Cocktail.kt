package com.kseniabl.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Cocktail (
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val createdAt: Long = 0,
    val recipe: String = "",
    val ingredients: List<String> = emptyList(),
    val image: String = ""
): Parcelable