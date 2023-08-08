package com.kseniabl.domain.usecase


import com.kseniabl.domain.database.entity.CocktailModel
import com.kseniabl.domain.repository.CocktailDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentCocktailsUseCase @Inject constructor(
    private val repository: CocktailDatabaseRepository
) {
    operator fun invoke(): Flow<List<CocktailModel>> = repository.getCocktails()

}