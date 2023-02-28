package com.dladukedev.recipes.ui.recipes

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeListScreen(
    onNavigateToDetails: (Long) -> Unit,
    vm: RecipeListViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsState()
    var test by remember {
        mutableStateOf(0)
    }
    val set: () -> Unit = { test++ }

    LaunchedEffect(state.recipeSelected) {
        state.recipeSelected?.let { selectedRecipe ->
            onNavigateToDetails(selectedRecipe.id)
            vm.clearSelectedRecipe()
        }
    }

    LaunchedEffect(Unit) {
        vm.loadRecipes()
    }

    RecipeListScreen(state, onClickAdd = set, onSelectRecipe = vm::onSelectRecipe)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    state: RecipeListViewModel.State,
    onClickAdd: () -> Unit,
    onSelectRecipe: (RecipeListItemDisplayModel) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onClickAdd() },
                modifier = Modifier.testTag("fab-test-tag").background(color = Color.Red)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Button")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding)
        ) {
            when (state.recipes) {
                is Result.Content -> RecipeList(
                    recipes = state.recipes.data,
                    onSelectRecipe = onSelectRecipe,
                    modifier = Modifier.fillMaxSize(),
                )

                Result.Error -> RecipesError()
                Result.Loading -> RecipesLoading()
            }
        }
    }
}

@Composable
fun RecipeList(
    recipes: ImmutableList<RecipeListItemDisplayModel>,
    onSelectRecipe: (RecipeListItemDisplayModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(recipes.list, { _, item ->  item.id }) { index, recipe ->
        RecipeListItem(recipe = recipe, onClick = onSelectRecipe, modifier = Modifier.testTag("Whatever"))
        }
    }
}
val modifierSpecial = Modifier.padding(32.dp)
@Composable
fun RecipesError(modifier: Modifier = Modifier) {
    Text("ERROR TODO", modifier = Modifier
        .padding(10.dp)
        .then(modifierSpecial))
}

@Preview
@Composable
fun PreviewRecipesError() {
    RecipeListItem(
        recipe = RecipeListItemDisplayModel(id = 0, name = "TODO Name", null, 2, null),
        {}
    )
}

@Composable
fun RecipesLoading(modifier: Modifier = Modifier) {
    Text("Loading...", modifier = modifier)
}

@Composable
fun RecipeListItem(
    recipe: RecipeListItemDisplayModel,
    onClick: (RecipeListItemDisplayModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val fadeAnimation = remember {
        Animatable(0f)
    }

   LaunchedEffect(key1 = Unit)  {
fadeAnimation.animateTo(1f, tween(5005, easing = LinearEasing))
   }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.clickable { onClick(recipe) }) {
        AsyncImage(
            model = recipe.image,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(text = recipe.name, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.alpha(fadeAnimation.value))
            Text(text = "${recipe.totalTime} minutes", style = MaterialTheme.typography.labelSmall)
            recipe.description?.let {
                Text(text = it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}