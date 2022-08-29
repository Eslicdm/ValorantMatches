package com.eslirodrigues.valorantmatches.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.whenCreated
import androidx.navigation.NavGraphBuilder
import com.eslirodrigues.valorantmatches.ui.screen.MatchesScreen
import com.eslirodrigues.valorantmatches.ui.screen.TourScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(showAd: () -> Unit) {
    val navController = rememberAnimatedNavController()
    var adCount by rememberSaveable { mutableStateOf(0) }

    AnimatedNavHost(navController = navController, startDestination = NavRoute.MatchesScreen.route) {
        composableSlideVertically(route = NavRoute.MatchesScreen.route) { MatchesScreen(navController) }
        composableSlideVertically(route = NavRoute.TourScreen.route) {
            val currentBackStackEntry = navController.currentBackStackEntry
            LaunchedEffect(Unit) {
                currentBackStackEntry?.whenCreated {
                    if (adCount % 3 == 0 ) showAd()
                    adCount++
                }
            }
            val tourName = currentBackStackEntry?.arguments?.getString(ArgsKeys.TOUR) ?: ""
            val lastTourName = currentBackStackEntry?.arguments?.getString(ArgsKeys.LAST_TOUR) ?: ""
            TourScreen(navController = navController, tourName = tourName, lastTourName = lastTourName)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composableSlideVertically(
    route: String,
    duration: Int = 700, // 1000 - 400
    enterOffset: Int = 2000, // 300 - 1000
    exitOffset: Int = -2000,
    popEnterOffset: Int = -2000,
    popExitOffset: Int = 2000,
    screen: @Composable () -> Unit
) {
    composable(
        route = route,
        enterTransition = { slideInVertically(tween(duration)) { enterOffset } },
        exitTransition = { slideOutVertically(tween(duration)){ exitOffset } },
        popEnterTransition = { slideInVertically(tween(duration)) { popEnterOffset } },
        popExitTransition = { slideOutVertically(tween(duration)) { popExitOffset } },
        content = { screen() }
    )
}