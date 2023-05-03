package com.salazar.lordhosting.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.salazar.lordhosting.core.ui.LordHostingAppState
import com.salazar.lordhosting.core.ui.LordHostingBottomBar
import com.salazar.lordhosting.core.ui.LordHostingUiState


@Composable
fun LordHostingNavGraph(
    uiState: LordHostingUiState,
    appState: LordHostingAppState,
) {
    val startDestination = LordHostingDestinations.AUTH_ROUTE

    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: MainDestinations.SERVERS_ROUTE
    val navActions = appState.navActions

    val hide =
        navBackStackEntry?.destination?.hierarchy?.any { it.route == LordHostingDestinations.SERVER_ROUTE } == true
                || navBackStackEntry?.destination?.hierarchy?.any { it.route == LordHostingDestinations.SETTING_ROUTE } == true
                || navBackStackEntry?.destination?.hierarchy?.any { it.route == LordHostingDestinations.AUTH_ROUTE } == true
//                || currentRoute.contains(ServerDestinations.SERVER_ROUTE)
    ModalBottomSheetLayout(
        bottomSheetNavigator = appState.bottomSheetNavigator,
        sheetShape = MaterialTheme.shapes.extraLarge,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetElevation = 0.dp,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(appState.snackBarHostState) },
            bottomBar = {
                AnimatedVisibility(
                    visible = !hide,
                ) {
                    LordHostingBottomBar(
                        picture = "",
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            appState.navController.navigate(route)
                        },
                    )
                }
            },
        ) { innerPadding ->
            AnimatedNavHost(
                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                route = LordHostingDestinations.ROOT_ROUTE,
                navController = appState.navController,
                startDestination = startDestination,
            ) {
                authNavGraph(
                    navActions = navActions,
                )
                serverNavGraph(
                    navActions = navActions,
                )
                mainNavGraph(
                    appState = appState,
                )
            }
        }
    }
}