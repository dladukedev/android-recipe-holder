package com.dladukedev.recipes.testing

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dladukedev.recipes.domain.recipes.Ingredient
import com.dladukedev.recipes.domain.recipes.Instruction
import com.dladukedev.recipes.domain.recipes.Recipe
import com.dladukedev.recipes.ui.recipes.RecipeDetailsScreen
import com.dladukedev.recipes.ui.recipes.RecipeDetailsViewModel
import com.dladukedev.recipes.ui.recipes.Result
import com.dladukedev.recipes.ui.theme.RecipesTheme
import org.junit.Rule
import org.junit.Test

class RecipeDetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun testRecipeDetailsScreen() {
        // Start the app
        composeTestRule.setContent {
            RecipesTheme  {
                RecipeDetailsScreen(
                    state = RecipeDetailsViewModel.State(
                        recipe = Result.Content(Recipe(
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
                        ))
                    )
                )
            }
        }

        composeTestRule.onNodeWithText("Test Recipe").assertIsDisplayed()
    }
}