package com.kseniabl.cocktailsuperbar.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.gson.Gson
import com.kseniabl.makecocktail.ui.navigation.createCocktailScreen
import com.kseniabl.makecocktail.ui.navigation.navigateToCreateCocktail
import com.kseniabl.mycocktails.navigation.detailCocktailScreen
import com.kseniabl.mycocktails.navigation.mycocktailsRoute
import com.kseniabl.mycocktails.navigation.mycocktailsScreen
import com.kseniabl.mycocktails.navigation.navigateToDetailCocktail
import com.kseniabl.mycocktails.navigation.navigateToMycocktails

@Composable
fun CocktailNavHost(
    navController: NavHostController
) {
    NavHost(navController, startDestination = mycocktailsRoute) {
        mycocktailsScreen(navController::navigateToCreateCocktail) { cocktail ->
            val json = Uri.encode(Gson().toJson(cocktail))
            navController.navigateToDetailCocktail("detailcocktail_route/$json")
        }
        createCocktailScreen(navController::navigateToMycocktails)
        detailCocktailScreen()
    }
}