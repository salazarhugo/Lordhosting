package com.salazar.lordhosting.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.salazar.lordhosting.core.ui.LordHostingAppState
import com.salazar.lordhosting.files.ui.FilesRoute
import com.salazar.lordhosting.files.ui.edit.EditFileRoute
import com.salazar.lordhosting.server.ui.backups.BackupsRoute
import com.salazar.lordhosting.server.ui.console.ConsoleRoute
import com.salazar.lordhosting.server.ui.server.ServerRoute
import com.salazar.lordhosting.server.ui.startup.StartupRoute
import com.salazar.lordhosting.users.ui.UsersRoute
import com.salazar.lordhosting.users.ui.create.CreateUserRoute

fun NavGraphBuilder.serverNavGraph(
    appState: LordHostingAppState,
    navActions: LordHostingNavigationActions,
) {
    navigation(
        route = LordHostingDestinations.SERVER_ROUTE,
        startDestination = "${ServerDestinations.SERVER_ROUTE}/{serverID}",
    ) {
        composable(
            route = "${ServerDestinations.SERVER_ROUTE}/{serverID}",
        ) {
            ServerRoute(
                appState = appState,
                navActions = navActions,
            )
        }

        composable(
            route = "${ServerDestinations.CONSOLE_ROUTE}/{serverID}",
        ) {
            ConsoleRoute(
                navActions = navActions,
            )
        }

        composable(
            route = "${ServerDestinations.FILE_ROUTE}/{serverID}",
        ) {
            FilesRoute(
                appState = appState,
                navActions = navActions,
            )
        }

        composable(
            route = "${ServerDestinations.EDIT_FILE_ROUTE}/{serverID}/{file}",
        ) {
            EditFileRoute(
                appState = appState,
                navActions = navActions,
            )
        }

        composable(
            route = "${ServerDestinations.USERS_ROUTE}/{serverID}",
        ) {
            UsersRoute(
                appState = appState,
                navActions = navActions,
            )
        }

        composable(
            route = "${ServerDestinations.CREATE_USER_ROUTE}/{serverID}",
        ) {
            CreateUserRoute(
                appState = appState,
                navActions = navActions,
            )
        }

        composable(
            route = "${ServerDestinations.STARTUP_ROUTE}/{serverID}",
        ) {
            StartupRoute(
                appState = appState,
                navActions = navActions,
            )
        }

        composable(
            route = "${ServerDestinations.BACKUPS_ROUTE}/{serverID}",
        ) {
            BackupsRoute(
                appState = appState,
                navActions = navActions,
            )
        }
    }
}