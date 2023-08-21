package com.kseniabl.makecocktail.ui.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kseniabl.makecocktail.ui.CreateCocktailScreen

const val createCocktailRoute = "createCocktail_route?cocktailId={cocktailId}"

fun NavController.navigateToCreateCocktail(route: String, navOptions: NavOptions? = null) {
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.createCocktailScreen(navigateToMyCocktails: () -> Unit) {
    composable(
        route = createCocktailRoute,
        arguments = listOf(navArgument("cocktailId") { nullable = true })
    ) {
        val cocktailId = it.arguments?.getString("cocktailId")
        CreateCocktailScreen(navigateToMyCocktails, id = cocktailId?.toInt())
    }
}
