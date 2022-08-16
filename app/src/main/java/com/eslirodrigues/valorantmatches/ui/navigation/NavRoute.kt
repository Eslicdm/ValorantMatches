package com.eslirodrigues.valorantmatches.ui.navigation

sealed class NavRoute(val route: String) {
    object MatchesScreen: NavRoute(route = "matches_screen")
    object TourScreen: NavRoute(route = "tour_screen/{${ArgsKeys.TOUR}}/{${ArgsKeys.LAST_TOUR}}") {
        fun withArgs(tourName: String, lastTourName: String): String {
            return "tour_screen/$tourName/$lastTourName"
        }
    }
}