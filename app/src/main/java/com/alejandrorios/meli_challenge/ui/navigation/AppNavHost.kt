package com.alejandrorios.meli_challenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alejandrorios.meli_challenge.ui.screens.product_detail.ProductDetailScreen
import com.alejandrorios.meli_challenge.ui.screens.search_product.SearchProductScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            SearchProductScreen(navController)
        }
        composable(
            "${NavigationItem.ProductDetails.route}/{productId}",
            arguments = listOf(navArgument("productId") {
            type = NavType.StringType
        })) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")

            ProductDetailScreen(navController, productId = productId!!)
        }
    }
}

enum class Screen {
    HOME,
    PRODUCT_DETAILS,
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object ProductDetails : NavigationItem(Screen.PRODUCT_DETAILS.name)
}
