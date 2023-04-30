package com.salazar.lordhosting.core.navigation

import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.salazar.lordhosting.auth.ui.signin.SignInRoute

fun NavGraphBuilder.authNavGraph(
    navActions: LordHostingNavigationActions,
) {
    navigation(
        route = LordHostingDestinations.AUTH_ROUTE,
        startDestination = AuthDestinations.SIGN_IN_ROUTE,
    ) {
        composable(
            route = AuthDestinations.SIGN_IN_ROUTE,
        ) {
            SignInRoute(
                navActions = navActions,
            )
        }
    }
}