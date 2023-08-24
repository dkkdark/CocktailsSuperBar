package com.kseniabl.mycocktails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kseniabl.mycocktails.presentation.DetailScreen

const val detailCocktailRoute = "detailcocktail_route/{cocktailId}"

fun NavController.navigateToDetailCocktail(route: String, navOptions: NavOptions? = null) {
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.detailCocktailScreen(navigateToEditScreen: (Int?) -> Unit, navigateToMyCocktails: () -> Unit) {
    composable(
        route = detailCocktailRoute,
        arguments = listOf(navArgument("cocktailId") {type = NavType.IntType })
    ) { backStackEntry ->
        val cocktailId = backStackEntry.arguments?.getInt("cocktailId")
        DetailScreen(cocktailId, navigateToEditScreen = navigateToEditScreen,
            navigateToMyCocktails = navigateToMyCocktails)
    }
}