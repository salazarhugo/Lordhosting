package com.salazar.lordhosting.auth.ui.signin

sealed class SignInUIAction {
    object OnSignInClick : SignInUIAction()
    data class OnEmailChange(val email: String): SignInUIAction()
    data class OnPasswordChange(val password: String): SignInUIAction()
    data class OnApiKeyChange(val apiKey: String): SignInUIAction()
    data class OnWithApiKeyChange(val withApiKey: Boolean): SignInUIAction()
}
