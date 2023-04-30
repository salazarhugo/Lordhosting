package com.salazar.lordhosting.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions

/**
 * Stateful composable that displays the Navigation route for the Account screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun AccountRoute(
    viewModel: AccountViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    AccountScreen(
        uiState = uiState,
        onAccountUIAction = { action ->
            when(action) {
                is AccountUIAction.OnCurrentPasswordChange -> viewModel.updateCurrentPassword(action.currentPassword)
                is AccountUIAction.OnEmailChange -> viewModel.updateNewPassword(action.email)
                is AccountUIAction.OnNewPasswordChange -> viewModel.updateNewPassword(action.newPassword)
                AccountUIAction.OnSignOut -> {
                    viewModel.signOut {
                        navActions.navigateToSignIn()
                    }
                }
                AccountUIAction.OnUpdateEmailClick -> viewModel.onUpdateEmailClick()
                AccountUIAction.OnUpdatePasswordClick -> viewModel.onUpdatePasswordClick()
            }
        },
    )
}
