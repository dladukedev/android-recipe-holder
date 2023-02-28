package com.dladukedev.recipes.data.recipes

import com.dladukedev.recipes.domain.recipes.Ingredient
import com.dladukedev.recipes.domain.recipes.Instruction
import com.dladukedev.recipes.domain.recipes.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface RecipeDataSource {
    fun subscribeToRecipeList(): Flow<List<Recipe>>
    fun subscribeToRecipe(id: Long): Flow<Recipe?>
}

private val recipes = (1..100).map {
    Recipe(
        id = it.toLong(),
        name = "Recipe $it",
        description = "Recipe $it Description",
        image = "https://via.placeholder.com/200/000000/FFFFFF",
        cookTime = 10 * it,
        prepTime = 5 * it,
        ingredients = listOf(
            Ingredient(1, "Sugar", "1 Cup"),
            Ingredient(2, "Sugar", "1 Tbsp"),
            Ingredient(3, "Everything Nice", "4 Drops"),
        ),
        instructions = listOf(
            Instruction(1, 3, "Bake in oven, let cool, and serve"),
            Instruction(2, 1, "Get all the ingredients "),
            Instruction(3, 2, "Make Cookie dough instead and place on cookie sheet"),
        ),
    )
}

class RecipeDummyDataSource : RecipeDataSource {
    override fun subscribeToRecipeList(): Flow<List<Recipe>> {
        return flowOf(recipes)
    }

    override fun subscribeToRecipe(id: Long): Flow<Recipe?> {
        val recipe = recipes.find { it.id == id }
        return flowOf(recipe)
    }
}