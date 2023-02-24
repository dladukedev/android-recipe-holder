package com.dladukedev.recipes.ui.recipes

import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dladukedev.recipes.domain.recipes.LoadRecipeListUseCase
import com.dladukedev.recipes.domain.recipes.Recipe
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListViewModel(private val loadRecipes: LoadRecipeListUseCase) : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun loadRecipes() {
        viewModelScope.launch {

            try {
                loadRecipes.invoke()
                    .onStart {
                        _state.update { current ->
                            current.copy(recipes = Result.Loading)
                        }
                    }
                    .collect { recipes ->
                        val displayRecipes = recipes.map { RecipeListItemDisplayModel(it) }
                        _state.update { current ->
                            current.copy(recipes = Result.Content(displayRecipes.toImmutableList()))
                        }
                    }
            } catch (_: Exception) {
                _state.update { current ->
                    current.copy(recipes = Result.Error)
                }
            }
        }
    }

    fun onSelectRecipe(recipe: RecipeListItemDisplayModel) {
        _state.update {current ->
           current.copy(recipeSelected = recipe)
        }
    }

    fun clearSelectedRecipe() {
        _state.update { current ->
            current.copy(recipeSelected = null)
        }
    }

    data class State(
        val recipes: Result<ImmutableList<RecipeListItemDisplayModel>> = Result.Loading,
        val recipeSelected: RecipeListItemDisplayModel? = null,
    )
}

@Immutable
data class ImmutableList<T>(val list: List<T>)

fun <T> List<T>.toImmutableList(): ImmutableList<T> = ImmutableList(this)
sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Content<T>(val data: T) : Result<T>()
    object Error : Result<Nothing>()
}