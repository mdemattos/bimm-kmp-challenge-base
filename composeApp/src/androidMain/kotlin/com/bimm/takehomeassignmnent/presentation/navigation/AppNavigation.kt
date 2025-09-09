package com.bimm.takehomeassignmnent.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bimm.takehomeassignmnent.presentation.screen.SakeShopDetailScreen
import com.bimm.takehomeassignmnent.presentation.screen.SakeShopListScreen
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object SakeShopList : Screen("sake_shop_list")
    object SakeShopDetail : Screen("sake_shop_detail/{shopName}") {
        fun createRoute(shopName: String): String {
            val encodedName = URLEncoder.encode(shopName, StandardCharsets.UTF_8.toString())
            return "sake_shop_detail/$encodedName"
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SakeShopList.route
    ) {
        composable(Screen.SakeShopList.route) {
            SakeShopListScreen(
                viewModel = koinViewModel(),
                onShopClick = { shop ->
                    navController.navigate(Screen.SakeShopDetail.createRoute(shop.name))
                }
            )
        }
        
        composable(Screen.SakeShopDetail.route) { backStackEntry ->
            val shopName = backStackEntry.arguments?.getString("shopName") ?: ""
            val decodedName = java.net.URLDecoder.decode(shopName, StandardCharsets.UTF_8.toString())
            
            SakeShopDetailScreen(
                viewModel = koinViewModel(),
                shopName = decodedName,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}