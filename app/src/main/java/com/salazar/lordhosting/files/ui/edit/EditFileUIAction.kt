package com.salazar.lordhosting.files.ui.edit

import com.salazar.lordhosting.files.domain.models.File

sealed class EditFileUIAction {
    object OnBackPressed : EditFileUIAction()
    object OnSaveChanges : EditFileUIAction()
    data class OnContentChange(val content: String): EditFileUIAction()
}