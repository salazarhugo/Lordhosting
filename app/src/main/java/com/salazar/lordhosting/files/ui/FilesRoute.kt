package com.salazar.lordhosting.files.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions
import com.salazar.lordhosting.core.ui.LordHostingAppState

/**
 * Stateful composable that displays the Navigation route for the Files screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun FilesRoute(
    appState: LordHostingAppState,
    viewModel: FilesViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    BackHandler {
        val isRoot = uiState.currentRoute.isEmpty()
        if (isRoot)
            appState.openDrawer()
        else
            viewModel.onBackClick()
    }

    FilesScreen(
        uiState = uiState,
        onFilesUIAction = { action ->
            val serverID = uiState.serverID
            when(action) {
                FilesUIAction.OnConsoleClick -> navActions.navigateToConsole(serverID)
                FilesUIAction.OnBackPressed -> {
                    val isRoot = uiState.currentRoute.isEmpty()
                    if (isRoot)
                        appState.openDrawer()
                    else
                        viewModel.onBackClick()
                }
                is FilesUIAction.OnDirectoryClick -> viewModel.onDirectoryClick(action.name)
                is FilesUIAction.OnCreateFolder -> viewModel.onCreateFolder(action.name)
                is FilesUIAction.OnFileClick -> viewModel.onFileClick(action.file)
                is FilesUIAction.OnOpenBottomSheetChange -> viewModel.updateOpenSheet(action.openBottomSheet)
                is FilesUIAction.OnDeleteFile -> viewModel.deleteFile()
                FilesUIAction.OnDeleteActionClick -> {
                    viewModel.updateOpenSheet(false)
                    viewModel.updateOpenDeleteDialog(true)
                }

                is FilesUIAction.OnOpenDeleteDialogChange -> viewModel.updateOpenDeleteDialog(action.open)
            }
        },
    )
}
