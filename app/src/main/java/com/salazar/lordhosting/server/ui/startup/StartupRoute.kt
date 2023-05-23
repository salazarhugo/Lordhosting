package com.salazar.lordhosting.server.ui.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions
import com.salazar.lordhosting.core.ui.LordHostingAppState
import com.salazar.lordhosting.server.ui.backups.BackupsUIAction

/**
 * Stateful composable that displays the Navigation route for the Startup screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun StartupRoute(
    appState: LordHostingAppState,
    viewModel: StartupViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    StartupScreen(
        uiState = uiState,
        onStartupUIAction = { action ->
            when (action) {
                BackupsUIAction.OnBackPressed -> appState.openDrawer()
                else -> {}
            }
        },
    )
}
