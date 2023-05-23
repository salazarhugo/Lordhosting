package com.salazar.lordhosting.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions

/**
 * Stateful composable that displays the Navigation route for the Home screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onHomeUIAction = { action ->
            when(action) {
                HomeUIAction.OnSignOut -> {
                    viewModel.signOut {
                        navActions.navigateToSignIn()
                    }
                }
                HomeUIAction.OnUpdateEmailClick -> {}
                HomeUIAction.OnUpdatePasswordClick -> {}
            }
        },
    )
}
