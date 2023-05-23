package com.salazar.lordhosting.files.ui.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions
import com.salazar.lordhosting.core.ui.LordHostingAppState

@Composable
fun EditFileRoute(
    appState: LordHostingAppState,
    viewModel: EditFileViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    EditFileScreen(
        uiState = uiState,
        onEditFileUIAction = { action ->
            when(action) {
                EditFileUIAction.OnBackPressed -> navActions.navigateBack()
                is EditFileUIAction.OnContentChange -> viewModel.updateContent(action.content)
                EditFileUIAction.OnSaveChanges -> {
                    viewModel.saveChanges {
                        navActions.navigateBack()
                    }
                }
            }
        },
    )
}
