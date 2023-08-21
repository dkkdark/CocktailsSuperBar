package com.kseniabl.domain.usecase

import com.kseniabl.domain.models.Cocktail
import com.kseniabl.domain.repository.CocktailDatabaseRepository
import javax.inject.Inject

class SaveCocktailUseCase @Inject constructor(
    private val repository: CocktailDatabaseRepository
) {
    operator fun invoke(
        id: Int,
        name: String,
        description: String,
        recipe: String,
        ingredients: List<String>,
        date: Long,
        image: String
    ) {
        repository.insertOrUpdate(
            Cocktail(
                id = id,
                name = name,
                description = description,
                recipe = recipe,
                createdAt = date,
                ingredients = ingredients,
                image = image
            )
        )
    }
}