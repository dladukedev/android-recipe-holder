package com.dladukedev.recipes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dladukedev.recipes.ui.recipes.RecipeDetailsScreen
import com.dladukedev.recipes.ui.recipes.RecipeListScreen

@Composable
fun Router() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "recipe-list") {
        composable("recipe-list") {
            RecipeListScreen(onNavigateToDetails = { id -> navController.navigate("recipe-details/$id") })
        }
        composable("recipe-details/{id}") { RecipeDetailsScreen() }
    }
}