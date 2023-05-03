package com.salazar.lordhosting.server.ui.console

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions

/**
 * Stateful composable that displays the Navigation route for the Console screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun ConsoleRoute(
    viewModel: ConsoleViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    ConsoleScreen(
        uiState = uiState,
        onConsoleUIAction = { action ->
            when(action) {
                ConsoleUIAction.OnBackPressClick -> navActions.navigateBack()
                is ConsoleUIAction.OnCommandChange -> viewModel.updateCommand(action.command)
                ConsoleUIAction.OnSendCommandClick -> viewModel.sendCommand()
            }
        }
    )
}
