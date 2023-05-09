package com.salazar.lordhosting.files.ui

sealed class FilesUIAction {
    object OnBackPressed : FilesUIAction()
    object OnConsoleClick : FilesUIAction()
    data class OnDirectoryClick(val name: String) : FilesUIAction()
}