package com.salazar.lordhosting.files.ui

import com.salazar.lordhosting.files.domain.models.File

sealed class FilesUIAction {
    object OnBackPressed : FilesUIAction()
    object OnConsoleClick : FilesUIAction()
    data class OnDirectoryClick(val name: String) : FilesUIAction()
    data class OnCreateFolder(val name: String) : FilesUIAction()
    data class OnFileClick(val file: File) : FilesUIAction()
    object OnDeleteFile : FilesUIAction()
    object OnDeleteActionClick : FilesUIAction()

    data class OnOpenDeleteDialogChange(val open: Boolean): FilesUIAction()
    data class OnOpenBottomSheetChange(val openBottomSheet: Boolean): FilesUIAction()
}