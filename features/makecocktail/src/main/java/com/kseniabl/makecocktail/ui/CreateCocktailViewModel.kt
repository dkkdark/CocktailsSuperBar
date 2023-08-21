package com.kseniabl.makecocktail.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.domain.models.Cocktail
import com.kseniabl.domain.usecase.GetCocktailByIdUseCase
import com.kseniabl.domain.usecase.SaveCocktailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCocktailViewModel @Inject constructor(
    val saveCocktailUseCase: SaveCocktailUseCase,
    val getCocktailByIdUseCase: GetCocktailByIdUseCase
): ViewModel() {

    private val _state = MutableSharedFlow<SavingCocktailState>()
    val state = _state.asSharedFlow()

    private val _cocktail = MutableStateFlow(Cocktail())
    val cocktail = _cocktail.asStateFlow()

    fun getCocktail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val el = getCocktailByIdUseCase(id)
                if (el != null)
                    _cocktail.value = el
                else
                    _state.emit(SavingCocktailState.Error("Cocktail cannot be load. Please try later."))
            } catch (e: Exception) {
                _state.emit(SavingCocktailState.Error("Cocktail cannot be load. Please try later."))
                Log.e("qqq", "details view model getCocktail error: $e")
            }
        }
    }

    fun onEventListen(event: SavingCocktailEvent) {
        when (event) {
            is SavingCocktailEvent.ChangeName -> { titleChanged(event.name) }
            is SavingCocktailEvent.ChangeDescription -> { descriptionChanged(event.description) }
            is SavingCocktailEvent.ChangeIngredients -> { ingredientsChanged(event.ingredients) }
            is SavingCocktailEvent.ChangeRecipe -> { recipeChanged(event.recipe) }
        }
    }

    private fun titleChanged(title: String) {
        _cocktail.update {
            it.copy(name = title)
        }
    }

    private fun descriptionChanged(descr: String) {
        _cocktail.update {
            it.copy(description = descr)
        }
    }

    private fun recipeChanged(recipe: String) {
        _cocktail.update {
            it.copy(recipe = recipe)
        }
    }

    private fun ingredientsChanged(ingredients: List<String>) {
        _cocktail.update {
            it.copy(ingredients = ingredients)
        }
    }

    private fun imageAndTimeChanged(image: String?, time: Long) {
        _cocktail.update {
            it.copy(image = image ?: "", createdAt = time)
        }
    }

    fun checkFields(time: Long, fileName: String?) {
        if (_cocktail.value.name.length >= 3) {
            imageAndTimeChanged(fileName, time)
            saveCocktail()
        }
        else {
            viewModelScope.launch {
                _state.emit(SavingCocktailState.NotCorrectDate)
            }
        }
    }

    private fun saveCocktail() {
        viewModelScope.launch {
            try {
                val cocktail = _cocktail.value
                saveCocktailUseCase(
                    cocktail.id,
                    cocktail.name,
                    cocktail.description,
                    cocktail.recipe,
                    cocktail.ingredients,
                    cocktail.createdAt,
                    cocktail.image
                )
                _state.emit(SavingCocktailState.SuccessSaving)
            } catch (e: Exception) {
                _state.emit(SavingCocktailState.Error("Saving is unsuccessful"))
            }
        }
    }

    sealed interface SavingCocktailState {
        object Loading : SavingCocktailState
        object SuccessSaving : SavingCocktailState
        data class Error(val error: String) : SavingCocktailState
        object NotCorrectDate : SavingCocktailState
    }

    sealed interface SavingCocktailEvent {
        data class ChangeName(val name: String): SavingCocktailEvent
        data class ChangeDescription(val description: String): SavingCocktailEvent
        data class ChangeRecipe(val recipe: String): SavingCocktailEvent
        data class ChangeIngredients(val ingredients: List<String>): SavingCocktailEvent
    }
}