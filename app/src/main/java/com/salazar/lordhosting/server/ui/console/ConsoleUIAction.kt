package com.salazar.lordhosting.server.ui.console

sealed class ConsoleUIAction {
    object OnBackPressClick : ConsoleUIAction()
    object OnSendCommandClick: ConsoleUIAction()
    data class OnCommandChange(val command: String): ConsoleUIAction()
}
