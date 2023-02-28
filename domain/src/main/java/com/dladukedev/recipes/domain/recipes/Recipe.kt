package com.dladukedev.recipes.domain.recipes

data class Recipe(
    val id: Long,
    val name: String,
    val description: String?,
    val image: String?,
    val cookTime: Int,
    val prepTime: Int,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
)

data class Ingredient(
    val id: Long,
    val name: String,
    val quantity: String,
)

data class Instruction(
    val id: Long,
    val order: Int,
    val description: String,
)