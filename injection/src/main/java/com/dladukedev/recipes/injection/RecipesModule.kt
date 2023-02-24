package com.dladukedev.recipes.injection

import com.dladukedev.recipes.data.recipes.RecipeDataSource
import com.dladukedev.recipes.data.recipes.RecipeDummyDataSource
import com.dladukedev.recipes.data.recipes.RecipeRepositoryImpl
import com.dladukedev.recipes.domain.recipes.LoadRecipeListUseCase
import com.dladukedev.recipes.domain.recipes.LoadRecipeListUseCaseImpl
import com.dladukedev.recipes.domain.recipes.RecipeRepository
import com.dladukedev.recipes.ui.recipes.RecipeDetailsViewModel
import com.dladukedev.recipes.ui.recipes.RecipeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recipeModule = module {
    single<RecipeDataSource> { RecipeDummyDataSource() }
    single<RecipeRepository> { RecipeRepositoryImpl(get()) }
    single<LoadRecipeListUseCase> { LoadRecipeListUseCaseImpl(get()) }
    viewModel { RecipeListViewModel(get())}
    viewModel { RecipeDetailsViewModel()}
}