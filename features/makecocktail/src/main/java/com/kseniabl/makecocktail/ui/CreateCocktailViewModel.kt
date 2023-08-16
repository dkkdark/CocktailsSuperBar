package com.kseniabl.makecocktail.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kseniabl.domain.usecase.SaveCocktailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateCocktailViewModel @Inject constructor(
    val saveCocktailUseCase: SaveCocktailUseCase
): ViewModel() {

    private val _state = MutableStateFlow<SavingCocktailState?>(null)
    val state = _state.asStateFlow()

    fun checkFields(title: String, description: String, recipe: String, ingredients: List<String>,
                    time: Long, fileName: String?) {
        if (title.length >= 3) {
            saveCocktail(title, description, recipe, ingredients, time, fileName)
        }
        else {
            _state.value = SavingCocktailState.NotCorrectDate
        }
    }

    private fun saveCocktail(name: String, description: String, recipe: String, ingredients: List<String>,
                             time: Long, fileName: String?) {
        try {
            saveCocktailUseCase(name, description, recipe, ingredients, time, fileName)
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