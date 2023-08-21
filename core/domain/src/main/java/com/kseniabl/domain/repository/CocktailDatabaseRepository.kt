package com.kseniabl.domain.repository

import com.kseniabl.domain.models.Cocktail
import kotlinx.coroutines.flow.Flow

interface CocktailDatabaseRepository {
    fun getCocktails(): Flow<List<Cocktail>>
    suspend fun getCocktailById(id: Int): Cocktail?
    fun insertOrUpdate(cocktail: Cocktail)
    fun insertCocktail(cocktail: Cocktail)
    fun updateCocktail(cocktail: Cocktail)
    fun deleteCocktail(id: Int)
}