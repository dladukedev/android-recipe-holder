package com.dladukedev.recipes.testing

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dladukedev.recipes.Router
import com.dladukedev.recipes.data.recipes.RecipeDataSource
import com.dladukedev.recipes.domain.recipes.Ingredient
import com.dladukedev.recipes.domain.recipes.Instruction
import com.dladukedev.recipes.domain.recipes.Recipe
import com.dladukedev.recipes.injection.recipeModule
import com.dladukedev.recipes.ui.theme.RecipesTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

// https://developer.android.com/static/images/jetpack/compose/compose-testing-cheatsheet.png

val testModule = module {
    single<RecipeDataSource> {
        object : RecipeDataSource {
            val recipe = Recipe(
                id = 1,
                name = "Test Recipe",
                description = "Test Recipe Description",
                image = null,
                cookTime = 10,
                prepTime = 5,
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
            override fun subscribeToRecipeList(): Flow<List<Recipe>> {
                return flowOf(listOf(recipe))
            }

            override fun subscribeToRecipe(id: Long): Flow<Recipe?> {
                return flowOf(recipe)
            }

        }
    }
}

class RecipeNavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        startKoin{
            modules(recipeModule, testModule)
        }

        composeTestRule.setContent {
            RecipesTheme {
               Router()
            }
        }

        composeTestRule.onNodeWithTag("fab-test-tag").assertIsDisplayed()

        composeTestRule.onNodeWithText("Test Recipe").performClick()

        composeTestRule.onNodeWithText("Ingredients").assertIsDisplayed()
        composeTestRule.onNodeWithText("1 Cup - Sugar").assertIsDisplayed()
    }
}