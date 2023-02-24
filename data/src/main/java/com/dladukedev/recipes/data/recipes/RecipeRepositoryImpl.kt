package com.dladukedev.recipes.data.recipes

import com.dladukedev.recipes.domain.recipes.Recipe
import com.dladukedev.recipes.domain.recipes.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RecipeRepositoryImpl(private val recipeDataSource: RecipeDataSource): RecipeRepository {
    override fun browseRecipes(): Flow<List<Recipe>> {
        return recipeDataSource.subscribeToRecipeList()
    }

    override fun readRecipe(id: Long): Flow<Recipe?> {
        return recipeDataSource.subscribeToRecipe(id)
    }
}