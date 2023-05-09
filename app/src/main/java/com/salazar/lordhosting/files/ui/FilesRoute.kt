package com.salazar.lordhosting.files.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions

/**
 * Stateful composable that displays the Navigation route for the Files screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun FilesRoute(
    viewModel: FilesViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    FilesScreen(
        uiState = uiState,
        onFilesUIAction = { action ->
            val serverID = uiState.serverID
            when(action) {
                FilesUIAction.OnConsoleClick -> navActions.navigateToConsole(serverID)
                FilesUIAction.OnBackPressed -> navActions.navigateBack()
                is FilesUIAction.OnDirectoryClick -> viewModel.onDirectoryClick(action.name)
            }
        },
    )
}
