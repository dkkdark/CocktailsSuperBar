package com.kseniabl.domain.usecase

import com.kseniabl.domain.repository.CocktailDatabaseRepository
import javax.inject.Inject

class DeleteCocktailUseCase @Inject constructor(
    private val repository: CocktailDatabaseRepository
) {
    operator fun invoke(id: Int) =
        repository.deleteCocktail(id)
}