package com.salazar.lordhosting.server.ui.server

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions

/**
 * Stateful composable that displays the Navigation route for the Server screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun ServerRoute(
    viewModel: ServerViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    ServerScreen(
        uiState = uiState,
        onServerUIAction = { action ->
            val serverID = uiState.serverID
            when(action) {
                ServerUIAction.OnConsoleClick -> navActions.navigateToConsole(serverID)
            }
        },
    )
}
