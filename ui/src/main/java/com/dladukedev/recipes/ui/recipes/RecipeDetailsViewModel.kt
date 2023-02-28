package com.dladukedev.recipes.ui.recipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dladukedev.recipes.domain.recipes.ReadRecipeUseCase
import com.dladukedev.recipes.domain.recipes.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    val readRecipe: ReadRecipeUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    val id = savedStateHandle.get<String>("id")!!.toLong()

    fun loadRecipe() {
        viewModelScope.launch {

            try {
                readRecipe(id)
                    .onStart {
                        _state.update { current ->
                            current.copy(recipe = Result.Loading)
                        }
                    }
                    .collect { recipeResult ->
                        _state.update { current ->
                            val recipe = when (recipeResult) {
                                null -> Result.Error
                                else -> Result.Content(recipeResult)
                            }
                            current.copy(recipe = recipe)
                        }
                    }
            } catch (_: Exception) {
                _state.update { current ->
                    current.copy(recipe = Result.Error)
                }
            }
        }
    }

    data class State(
        val recipe: Result<Recipe> = Result.Loading,
    )
}