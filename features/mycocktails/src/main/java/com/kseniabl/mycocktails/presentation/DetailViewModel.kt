package com.kseniabl.mycocktails.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.domain.usecase.DeleteCocktailUseCase
import com.kseniabl.domain.usecase.EditCocktailUseCase
import com.kseniabl.domain.models.Cocktail
import com.kseniabl.domain.usecase.GetCocktailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val deleteCocktailUseCase: DeleteCocktailUseCase,
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
    private val editCocktailUseCase: EditCocktailUseCase
): ViewModel() {

    private val _cocktail = MutableStateFlow<CocktailDetailsState>(CocktailDetailsState.Loading)
    val cocktail = _cocktail.asStateFlow()

    fun getCocktail(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (id != null) {
                    val el = getCocktailByIdUseCase(id)
                    if (el != null)
                        _cocktail.value = CocktailDetailsState.Success(el)
                    else
                        _cocktail.value = CocktailDetailsState.Error("Cocktail cannot be load. Please try later.")
                }
                else {
                    _cocktail.value = CocktailDetailsState.Error("Cocktail cannot be load. Please try later.")
                }
            } catch (e: Exception) {
                _cocktail.value = CocktailDetailsState.Error("Cocktail cannot be load. Please try later.")
                Log.e("qqq", "details view model getCocktail error: $e")
            }
        }
    }

    fun deleteCocktail(cocktail: Cocktail?) {
        cocktail?.let {
            if (cocktail.id != null)
                deleteCocktailUseCase(cocktail.id!!)
        }
    }

    fun editCocktail(cocktail: Cocktail) {
        editCocktailUseCase(cocktail)
    }

    sealed interface CocktailDetailsState {
        object Loading: CocktailDetailsState
        data class Success(val content: Cocktail): CocktailDetailsState
        data class Error(val error: String): CocktailDetailsState
    }
}