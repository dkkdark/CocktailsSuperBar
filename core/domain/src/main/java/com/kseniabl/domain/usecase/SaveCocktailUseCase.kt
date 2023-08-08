package com.kseniabl.domain.usecase

import android.util.Log
import com.kseniabl.domain.database.entity.CocktailModel
import com.kseniabl.domain.repository.CocktailDatabaseRepository
import javax.inject.Inject

class SaveCocktailUseCase @Inject constructor(
    private val repository: CocktailDatabaseRepository
) {
    operator fun invoke(
        name: String,
        description: String,
        recipe: String,
        ingredients: List<String>,
        date: Long
    ) {
        repository.insertCocktail(
            CocktailModel(
                name = name,
                description = description,
                recipe = recipe,
                createdAt = date,
                ingredients = ingredients
            )
        )
    }
}