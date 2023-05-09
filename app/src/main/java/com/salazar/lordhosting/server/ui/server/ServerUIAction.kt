package com.salazar.lordhosting.server.ui.server

sealed class ServerUIAction {
    object OnBackPressed : ServerUIAction()
    object OnConsoleClick : ServerUIAction()
    data class UpdatePowerState(val signal: String) : ServerUIAction()
}
