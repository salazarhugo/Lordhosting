package com.salazar.lordhosting.server.ui.startup

sealed class StartupUIAction {
    object OnBackPressed : StartupUIAction()
}
