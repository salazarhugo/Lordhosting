package com.salazar.lordhosting.users.ui.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions
import com.salazar.lordhosting.core.ui.LordHostingAppState

@Composable
fun CreateUserRoute(
    appState: LordHostingAppState,
    viewModel: CreateUserViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    CreateUserScreen(
        uiState = uiState,
        onCreateUserUIAction = { action ->
            when (action) {
                CreateUserUIAction.OnBackPressed -> navActions.navigateBack()
                is CreateUserUIAction.OnDeleteUser -> {}
                CreateUserUIAction.OnNewUserClick -> {
                }
                is CreateUserUIAction.OnEmailChange -> viewModel.updateEmail(action.email)
                CreateUserUIAction.OnCreateClick -> {
                    viewModel.onCreateClick {
                        navActions.navigateBack()
                    }
                }
                is CreateUserUIAction.OnTogglePermission -> viewModel.togglePermission(action.permission)
            }
        },
    )
}