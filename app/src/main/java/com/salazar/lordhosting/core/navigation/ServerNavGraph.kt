package com.salazar.lordhosting.core.navigation

import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.salazar.lordhosting.auth.ui.signin.SignInRoute
import com.salazar.lordhosting.server.ui.console.ConsoleRoute

fun NavGraphBuilder.serverNavGraph(
    navActions: LordHostingNavigationActions,
) {
    navigation(
        route = LordHostingDestinations.SERVER_ROUTE,
        startDestination = "${ServerDestinations.CONSOLE_ROUTE}/{serverID}",
    ) {
        composable(
            route = "${ServerDestinations.CONSOLE_ROUTE}/{serverID}",
        ) {
            ConsoleRoute(
                navActions = navActions,
            )
        }
    }
}