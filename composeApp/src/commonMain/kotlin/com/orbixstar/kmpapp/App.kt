package com.orbixstar.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.orbixstar.kmpapp.screens.detail.DetailScreen
import com.orbixstar.kmpapp.screens.list.ListScreen
import com.orbixstar.kmpapp.screens.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
object SplashDestination

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val objectId: Int)

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        val navController: NavHostController = rememberNavController()
        NavHost(navController = navController, startDestination = SplashDestination) {
            composable<SplashDestination> {
                SplashScreen(navigateToMain = {
                    navController.navigate(ListDestination) {
                        popUpTo(SplashDestination) { inclusive = true }
                    }
                })
            }
            composable<ListDestination> {
                ListScreen(navigateToDetails = { objectId ->
                    navController.navigate(DetailDestination(objectId))
                })
            }
            composable<DetailDestination> { backStackEntry ->
                DetailScreen(objectId = backStackEntry.toRoute<DetailDestination>().objectId,
                    navigateBack = {
                        navController.popBackStack()
                    })
            }
        }
    }
}
