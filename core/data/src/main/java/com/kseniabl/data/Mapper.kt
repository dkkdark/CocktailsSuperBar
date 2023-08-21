package com.kseniabl.data

import com.kseniabl.data.local.entity.CocktailModel
import com.kseniabl.domain.models.Cocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun CocktailModel.toDomain(): Cocktail {
    return Cocktail(
        id = this.id,
        name = this.name,
        description = this.description,
        createdAt = this.createdAt,
        recipe = this.recipe,
        ingredients = this.ingredients,
        image = this.image
    )
}

fun Cocktail.toEntity() =
    CocktailModel(
        id = this.id,
        name = this.name,
        description = this.description,
        createdAt = this.createdAt,
        recipe = this.recipe,
        ingredients = this.ingredients,
        image = this.image
    )

fun Flow<List<CocktailModel>>.mapCocktail(): Flow<List<Cocktail>> {
    return this.map {
        it.map { el ->
            Cocktail(
                el.id,
                el.name,
                el.description,
                el.createdAt,
                el.recipe,
                el.ingredients.sortedBy { el.createdAt },
                el.image
            )
        }
    }
}