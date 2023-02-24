package com.dladukedev.recipes.domain.recipes

import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    fun browseRecipes(): Flow<List<Recipe>>
    fun readRecipe(id: Long): Flow<Recipe?>
}
