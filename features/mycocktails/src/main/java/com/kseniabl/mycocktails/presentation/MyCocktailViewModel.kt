package com.kseniabl.mycocktails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.domain.database.entity.CocktailModel
import com.kseniabl.domain.usecase.GetRecentCocktailsUseCase
import com.kseniabl.mycocktails.R
import com.kseniabl.mycocktails.entity.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyCocktailViewModel @Inject constructor(
    val getRecentCocktailsUseCase: GetRecentCocktailsUseCase
): ViewModel() {

    val cocktails: StateFlow<List<Cocktail>?> = getRecentCocktailsUseCase()
        .mapCocktail().stateIn(
        scope = viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    private fun Flow<List<CocktailModel>>.mapCocktail(): Flow<List<Cocktail>> {
        return this.map {
            it.map { el ->
                Cocktail(
                    el.name,
                    el.description,
                    el.recipe,
                    el.ingredients.sortedBy { el.createdAt },
                    el.image
                )
            }
        }
    }
}