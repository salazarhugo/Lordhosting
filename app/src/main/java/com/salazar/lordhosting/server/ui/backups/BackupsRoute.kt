package com.salazar.lordhosting.server.ui.backups

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions
import com.salazar.lordhosting.core.ui.LordHostingAppState
import kotlinx.coroutines.launch

/**
 * Stateful composable that displays the Navigation route for the Startup screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun BackupsRoute(
    appState: LordHostingAppState,
    viewModel: BackupsViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    BackupsScreen(
        uiState = uiState,
        onBackupsUIAction = { action ->
            when(action) {
                BackupsUIAction.OnBackPressed -> appState.openDrawer()
                BackupsUIAction.OnCreateBackup -> viewModel.createBackup()
                is BackupsUIAction.OnDelete -> {
                    viewModel.deleteBackup {
                        viewModel.updateOpenBottomSheet(false)
                    }
                }
                is BackupsUIAction.OnSelectBackup -> viewModel.updateSelectedBackups(action.backup)
                is BackupsUIAction.OnOpenBottomSheetChange -> viewModel.updateOpenBottomSheet(action.openBottomSheet)
            }
        },
    )
}
