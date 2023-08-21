package com.kseniabl.domain.usecase

import com.kseniabl.domain.repository.CocktailDatabaseRepository
import javax.inject.Inject

class GetCocktailByIdUseCase @Inject constructor(
    private val repository: CocktailDatabaseRepository
) {
    suspend operator fun invoke(id: Int) =
        repository.getCocktailById(id)
}