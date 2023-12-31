package com.kseniabl.domain.usecase


import com.kseniabl.domain.models.Cocktail
import com.kseniabl.domain.repository.CocktailDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentCocktailsUseCase @Inject constructor(
    private val repository: CocktailDatabaseRepository
) {
    operator fun invoke(): Flow<List<Cocktail>> = repository.getCocktails()

}