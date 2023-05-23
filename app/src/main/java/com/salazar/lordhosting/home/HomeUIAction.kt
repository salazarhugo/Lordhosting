package com.salazar.lordhosting.home

sealed class HomeUIAction {
    object OnSignOut : HomeUIAction()
    object OnUpdatePasswordClick : HomeUIAction()
    object OnUpdateEmailClick : HomeUIAction()
}
