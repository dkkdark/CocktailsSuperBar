package com.kseniabl.mycocktails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kseniabl.mycocktails.MyCocktailsScreen

const val mycocktailsRoute = "mycocktails_route"

fun NavController.navigateToMycocktails(navOptions: NavOptions? = null) {
    this.navigate(mycocktailsRoute, navOptions)
}

fun NavGraphBuilder.mycocktailsScreen(navigateToCreateCocktail: () -> Unit, onItemClicked: () -> Unit) {
    composable(route = mycocktailsRoute) {
        MyCocktailsScreen(navigateToCreateCocktail, onItemClicked = onItemClicked)
    }
}