package com.salazar.lordhosting.account.ui

sealed class AccountUIAction {
    object OnSignOut : AccountUIAction()
    object OnUpdatePasswordClick : AccountUIAction()
    object OnUpdateEmailClick : AccountUIAction()
    data class OnCurrentPasswordChange(val currentPassword: String): AccountUIAction()
    data class OnNewPasswordChange(val newPassword: String): AccountUIAction()
    data class OnEmailChange(val email: String): AccountUIAction()
}
