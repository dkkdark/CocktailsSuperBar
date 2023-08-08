package com.kseniabl.makecocktail.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kseniabl.makecocktail.ui.CreateCocktailScreen

const val createCocktailRoute = "createCocktail_route"

fun NavController.navigateToCreateCocktail(navOptions: NavOptions? = null) {
    this.navigate(createCocktailRoute, navOptions)
}

fun NavGraphBuilder.createCocktailScreen() {
    composable(route = createCocktailRoute) {
        CreateCocktailScreen()
    }
}