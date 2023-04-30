package com.salazar.lordhosting.auth.ui.signin

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.lordhosting.core.navigation.LordHostingNavigationActions
import com.salazar.lordhosting.core.ui.components.LoadingScreen

/**
 * Stateful composable that displays the Navigation route for the SignIn screen.
 *
 * @param viewModel ViewModel that handles the business logic of this screen
 */
@Composable
fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    navActions: LordHostingNavigationActions,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val signedIn = uiState.isSignedIn

    when(signedIn) {
        true -> {
            LaunchedEffect(Unit) {
                navActions.navigateToMain()
            }
        }
        false -> {
            SignInScreen(
                uiState = uiState,
                onSignInUIAction = { action ->
                   when(action) {
                       is SignInUIAction.OnApiKeyChange -> viewModel.onApiKeyChange(action.apiKey)
                       is SignInUIAction.OnEmailChange -> viewModel.onEmailChange(action.email)
                       is SignInUIAction.OnPasswordChange -> viewModel.onPasswordChange(action.password)
                       SignInUIAction.OnSignInClick -> viewModel.onSignInClick()
                       is SignInUIAction.OnWithApiKeyChange -> viewModel.onWithApiKeyChange(action.withApiKey)
                   }
                },
            )
        }
        null -> LoadingScreen()
    }
}