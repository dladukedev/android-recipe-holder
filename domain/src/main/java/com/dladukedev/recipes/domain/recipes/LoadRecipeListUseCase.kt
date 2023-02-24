package com.dladukedev.recipes.domain.recipes

import kotlinx.coroutines.flow.Flow

interface LoadRecipeListUseCase {
    operator fun invoke(): Flow<List<Recipe>>
}
class LoadRecipeListUseCaseImpl(private val recipeRepository: RecipeRepository): LoadRecipeListUseCase {
    override fun invoke(): Flow<List<Recipe>> {
        return recipeRepository.browseRecipes()
    }
}