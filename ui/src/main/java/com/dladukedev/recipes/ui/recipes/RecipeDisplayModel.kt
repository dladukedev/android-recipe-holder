package com.dladukedev.recipes.ui.recipes

import com.dladukedev.recipes.domain.recipes.Recipe

data class RecipeListItemDisplayModel(
    val id: Long,
    val name: String,
    val description: String?,
    val totalTime: Int,
    val image: String?,
) {
    constructor(recipe: Recipe) : this(
        id = recipe.id,
        name = recipe.name,
        description = recipe.description,
        totalTime = recipe.cookTime + recipe.prepTime,
        image = recipe.image,
    )
}
