package com.kseniabl.cocktailsuperbar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kseniabl.makecocktail.ui.navigation.createCocktailScreen
import com.kseniabl.makecocktail.ui.navigation.navigateToCreateCocktail
import com.kseniabl.mycocktails.navigation.mycocktailsRoute
import com.kseniabl.mycocktails.navigation.mycocktailsScreen

@Composable
fun CocktailNavHost(
    navController: NavHostController
) {
    NavHost(navController, startDestination = mycocktailsRoute) {
        mycocktailsScreen(navController::navigateToCreateCocktail)
        createCocktailScreen()
    }
}