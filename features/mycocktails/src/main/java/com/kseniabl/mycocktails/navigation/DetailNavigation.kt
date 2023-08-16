package com.kseniabl.mycocktails.navigation

import android.os.Build
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kseniabl.mycocktails.entity.Cocktail
import com.kseniabl.mycocktails.presentation.DetailScreen

const val detailCocktailRoute = "detailcocktail_route/{cocktail}"

fun NavController.navigateToDetailCocktail(route: String, navOptions: NavOptions? = null) {
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.detailCocktailScreen() {
    composable(
        route = detailCocktailRoute,
        arguments = listOf(navArgument("cocktail") {type = CocktailTypeConverter() })
    ) { backStackEntry ->
        val cocktail = if (Build.VERSION.SDK_INT >= 33) {
            backStackEntry.arguments?.getParcelable("cocktail", Cocktail::class.java)
        } else {
            @Suppress("DEPRECATION") backStackEntry.arguments?.getParcelable("cocktail")
        }
        DetailScreen(cocktail)
    }
}