package com.kseniabl.mycocktails.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.kseniabl.mycocktails.entity.Cocktail

class CocktailTypeConverter : NavType<Cocktail>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Cocktail? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Cocktail {
        return Gson().fromJson(value, Cocktail::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Cocktail) {
        bundle.putParcelable(key, value)
    }
}