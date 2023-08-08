package com.kseniabl.makecocktail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.domain.database.entity.Cocktail
import com.kseniabl.domain.usecase.GetRecentCocktailsUseCase
import com.kseniabl.domain.usecase.SaveCocktailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CreateCocktailViewModel @Inject constructor(
    val saveCocktailUseCase: SaveCocktailUseCase,
    val getRecentCocktailsUseCase: GetRecentCocktailsUseCase
): ViewModel() {

    val cocktails: StateFlow<List<Cocktail>> = getRecentCocktailsUseCase().map {
        emptyList<Cocktail>()
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    private val _state = MutableStateFlow<SavingCocktailState>(SavingCocktailState.Loading)
    val state = _state.asStateFlow()

    fun checkFields(title: String, description: String, recipe: String, ingredients: List<String>, time: Long) {
        if (title.length >= 3) {
            saveCocktail(title, description, recipe, ingredients, time)
        }
        else {
            _state.value = SavingCocktailState.NotCorrectDate
        }
    }

    private fun saveCocktail(name: String, description: String, recipe: String, ingredients: List<String>, time: Long) {
        try {
            saveCocktailUseCase(name, description, recipe, ingredients, time)
            _state.value = SavingCocktailState.Success
        } catch (e: Exception) {
            _state.value = SavingCocktailState.Error
        }
    }


    sealed interface SavingCocktailState {
        object Loading : SavingCocktailState
        object Success : SavingCocktailState
        object Error : SavingCocktailState
        object NotCorrectDate : SavingCocktailState
    }
}