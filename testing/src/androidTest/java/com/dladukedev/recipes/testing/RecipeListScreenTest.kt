package com.dladukedev.recipes.testing

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dladukedev.recipes.domain.recipes.Ingredient
import com.dladukedev.recipes.domain.recipes.Instruction
import com.dladukedev.recipes.domain.recipes.Recipe
import com.dladukedev.recipes.ui.recipes.RecipeDetailsScreen
import com.dladukedev.recipes.ui.recipes.RecipeDetailsViewModel
import com.dladukedev.recipes.ui.recipes.RecipeListItemDisplayModel
import com.dladukedev.recipes.ui.recipes.RecipeListScreen
import com.dladukedev.recipes.ui.recipes.RecipeListViewModel
import com.dladukedev.recipes.ui.recipes.Result
import com.dladukedev.recipes.ui.recipes.toImmutableList
import com.dladukedev.recipes.ui.theme.RecipesTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class RecipeListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun testRecipeListScreen() {
        var wasClicked = false
        val onClickAdd = { wasClicked = true }
        // Start the app
        composeTestRule.setContent {
            RecipesTheme {
                RecipeListScreen(
                    state = RecipeListViewModel.State(
                        recipes = Result.Content(
                            listOf(
                                RecipeListItemDisplayModel(
                                    1,
                                    "Recipe 1",
                                    null,
                                    100,
                                    null
                                ),
                                RecipeListItemDisplayModel(
                                    2,
                                    "Recipe 2",
                                    null,
                                    100,
                                    null
                                ),
                                RecipeListItemDisplayModel(
                                    3,
                                    "Recipe 3",
                                    null,
                                    100,
                                    null
                                ),
                                RecipeListItemDisplayModel(
                                    4,
                                    "Recipe 4",
                                    null,
                                    100,
                                    null
                                ),
                                RecipeListItemDisplayModel(
                                    5,
                                    "Recipe 5",
                                    null,
                                    100,
                                    null
                                ),
                            ).toImmutableList()
                        )
                    ),
                    onClickAdd = onClickAdd,
                    onSelectRecipe = { }
                )

            }
        }

        composeTestRule.onNodeWithText("Recipe 3").assertIsDisplayed()
       composeTestRule.onNodeWithTag("fab-test-tag").performClick()
        assertTrue(wasClicked)
    }
}