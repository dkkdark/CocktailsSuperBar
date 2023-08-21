package com.kseniabl.mycocktails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.domain.usecase.GetRecentCocktailsUseCase
import com.kseniabl.domain.models.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyCocktailViewModel @Inject constructor(
    val getRecentCocktailsUseCase: GetRecentCocktailsUseCase
): ViewModel() {

    val cocktails: StateFlow<List<Cocktail>?> = getRecentCocktailsUseCase()
            .stateIn(
                scope = viewModelScope,
                initialValue = null,
                started = SharingStarted.WhileSubscribed(5_000),
            )

}

