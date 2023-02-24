package com.dladukedev.recipes.domain.recipes

import kotlinx.coroutines.flow.Flow

interface ReadRecipeUseCase {
    operator fun invoke(id: Long): Flow<Recipe?>
}

class ReadRecipeUseCaseImpl(private val recipeRepository: RecipeRepository): ReadRecipeUseCase {
    override fun invoke(id: Long): Flow<Recipe?> {
        return recipeRepository.readRecipe(id)
    }

}