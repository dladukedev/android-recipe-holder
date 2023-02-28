package com.dladukedev.recipes.ui.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dladukedev.recipes.domain.recipes.Ingredient
import com.dladukedev.recipes.domain.recipes.Instruction
import com.dladukedev.recipes.domain.recipes.Recipe
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeDetailsScreen(vm: RecipeDetailsViewModel = koinViewModel()) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadRecipe()
    }

    RecipeDetailsScreen(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    state: RecipeDetailsViewModel.State,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding)
        ) {
            when (state.recipe) {
                is Result.Content -> RecipeDetails(
                    recipe = state.recipe.data,
                )

                Result.Error -> RecipesError()
                Result.Loading -> RecipesLoading()
            }
        }
    }
}
@Preview
@Composable
fun Test() {

    RecipeDetailsScreen(
        state = RecipeDetailsViewModel.State(
            recipe = Result.Content(Recipe(
                id = 1,
                name = "Test Recipe",
                description = "Test the Recipe Description",
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
@Composable
fun RecipeDetails(recipe: Recipe) {
    val totalTime = remember {
        recipe.prepTime + recipe.cookTime
    }
    val instructions = remember {
        recipe.instructions.sortedBy { it.order }
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(red = 46, green = 51, blue = 57))
        ) {
            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .align(Center)
            )
        }
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Prep Time: ${recipe.prepTime}",
                style = MaterialTheme.typography.labelMedium
            )
            Text(text = " - ")
            Text(
                text = "Cook Time: ${recipe.cookTime}",
                style = MaterialTheme.typography.labelMedium
            )
            Text(text = " - ")
            Text(text = "Total Time: $totalTime", style = MaterialTheme.typography.labelMedium)
        }
        recipe.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        recipe.ingredients.forEach { ingredient ->
            Text(
                text = "${ingredient.quantity} - ${ingredient.name}",
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Directions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        instructions.forEach { instruction ->
            Text(
                text = "${instruction.order}. ${instruction.description}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}