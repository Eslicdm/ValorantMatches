package com.eslirodrigues.valorantmatches.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eslirodrigues.valorantmatches.ui.screen.MatchesScreen
import com.eslirodrigues.valorantmatches.ui.screen.TourScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoute.MatchesScreen.route) {
        composable(route = NavRoute.MatchesScreen.route) { MatchesScreen(navController) }
        composable(route = NavRoute.TourScreen.route) {
            val tourName = it.arguments?.getString(ArgsKeys.TOUR, "") ?: ""
            val lastTourName = it.arguments?.getString(ArgsKeys.LAST_TOUR, "") ?: ""
            TourScreen(navController = navController, tourName = tourName, lastTourName = lastTourName)
        }
    }
}