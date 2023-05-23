package com.salazar.lordhosting.server.ui.backups

import com.salazar.lordhosting.server.domain.models.Backup

sealed class BackupsUIAction {
    object OnBackPressed : BackupsUIAction()
    object OnCreateBackup : BackupsUIAction()
    object OnDelete: BackupsUIAction()
    data class OnSelectBackup(val backup: Backup): BackupsUIAction()
    data class OnOpenBottomSheetChange(val openBottomSheet: Boolean): BackupsUIAction()
}
