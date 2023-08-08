package com.kseniabl.domain.repository

import com.kseniabl.domain.database.entity.CocktailModel
import kotlinx.coroutines.flow.Flow

interface CocktailDatabaseRepository {
    fun getCocktails(): Flow<List<CocktailModel>>
    fun insertCocktail(cocktail: CocktailModel)
    fun updateCocktail(cocktail: CocktailModel)
    fun deleteCocktail(cocktail: CocktailModel)
}