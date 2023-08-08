package com.kseniabl.domain.usecase

import com.kseniabl.domain.database.entity.Cocktail
import com.kseniabl.domain.database.entity.CocktailModel
import com.kseniabl.domain.repository.CocktailDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentCocktailsUseCase @Inject constructor(
    private val repository: CocktailDatabaseRepository
) {
    operator fun invoke(): Flow<List<Cocktail>> = repository.getCocktails().mapCocktail()

    // let's imagine our models are different like it would be in real app
    private fun Flow<List<CocktailModel>>.mapCocktail(): Flow<List<Cocktail>> {
        return this.map {
            it.map { el ->
                Cocktail(el.name, el.description, el.recipe, el.ingredients.sortedBy { el.createdAt })
            }
        }
    }
}