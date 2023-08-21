package com.kseniabl.domain.usecase

import com.kseniabl.domain.models.Cocktail
import com.kseniabl.domain.repository.CocktailDatabaseRepository
import javax.inject.Inject

class EditCocktailUseCase @Inject constructor(
    private val repository: CocktailDatabaseRepository
) {
    operator fun invoke(cocktail: Cocktail) =
        repository.updateCocktail(cocktail)
}
