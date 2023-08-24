package com.kseniabl.mycocktails.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.domain.usecase.DeleteCocktailUseCase
import com.kseniabl.domain.models.Cocktail
import com.kseniabl.domain.usecase.GetCocktailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val deleteCocktailUseCase: DeleteCocktailUseCase,
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase
): ViewModel() {

    private val _cocktailState = MutableSharedFlow<CocktailDetailsState>()
    val cocktailState = _cocktailState.asSharedFlow()

    private val _cocktail = MutableStateFlow(Cocktail())
    val cocktail = _cocktail.asStateFlow()

    fun getCocktail(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (id != null) {
                    val el = getCocktailByIdUseCase(id)
                    if (el != null)
                        _cocktail.value = el
                    else
                        _cocktailState.emit(CocktailDetailsState.Error("Cocktail cannot be load. Please try later."))
                }
                else {
                    _cocktailState.emit(CocktailDetailsState.Error("Cocktail cannot be load. Please try later."))
                }
            } catch (e: Exception) {
                _cocktailState.emit(CocktailDetailsState.Error("Cocktail cannot be load. Please try later."))
                Log.e("qqq", "details view model getCocktail error: $e")
            }
        }
    }

    fun showDialog(state: Boolean) {
        viewModelScope.launch {
            _cocktailState.emit(CocktailDetailsState.ShowDialog(state))
        }
    }

    fun deleteCocktail(cocktail: Cocktail?) {
        cocktail?.let {
            deleteCocktailUseCase(cocktail.id)
        }
    }

    sealed interface CocktailDetailsState {
        object Loading: CocktailDetailsState

        data class ShowDialog(val show: Boolean): CocktailDetailsState
        data class Error(val error: String): CocktailDetailsState
    }
}