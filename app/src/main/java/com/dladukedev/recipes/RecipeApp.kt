package com.dladukedev.recipes

import android.app.Application
import com.dladukedev.recipes.injection.recipeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RecipeApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            // Reference Android context
            androidContext(this@RecipeApp)

            modules(recipeModule)
        }
    }
}