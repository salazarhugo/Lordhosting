package com.salazar.lordhosting.users.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions
import com.salazar.lordhosting.core.ui.LordHostingAppState

/**
 * Stateful composable that displays the Navigation route for the Users screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun UsersRoute(
    appState: LordHostingAppState,
    viewModel: UsersViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    UsersScreen(
        uiState = uiState,
        onUsersUIAction = { action ->
            when (action) {
                UsersUIAction.OnBackPressed -> appState.openDrawer()
                is UsersUIAction.OnDeleteUser -> viewModel.deleteUser(action.user)
                UsersUIAction.OnNewUserClick -> {
                    navActions.navigateToCreateUser(uiState.serverID)
                }
            }
        },
    )
}
