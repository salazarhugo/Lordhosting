package com.salazar.lordhosting.server.ui.servers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions

/**
 * Stateful composable that displays the Navigation route for the Servers screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun ServersRoute(
    viewModel: ServersViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    ServersScreen(
        uiState = uiState,
        onServerClick = {
            navActions.navigateToConsole(it.attributes.uuid)
        }
    )
}
