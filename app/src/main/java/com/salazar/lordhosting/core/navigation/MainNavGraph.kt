package com.salazar.lordhosting.core.navigation

import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.salazar.lordhosting.account.ui.AccountRoute
import com.salazar.lordhosting.core.ui.LordHostingAppState
import com.salazar.lordhosting.server.ui.servers.ServersRoute


fun NavGraphBuilder.mainNavGraph(
    appState: LordHostingAppState,
) {
    navigation(
        route = LordHostingDestinations.MAIN_ROUTE,
        startDestination = MainDestinations.SERVERS_ROUTE,
    ) {
        composable(
            route = MainDestinations.SERVERS_ROUTE,
        ) {
            ServersRoute(
                navActions = appState.navActions,
            )
        }

        composable(
            route = MainDestinations.ACCOUNT_ROUTE,
        ) {
            AccountRoute(
                navActions = appState.navActions,
            )
        }
    }
}