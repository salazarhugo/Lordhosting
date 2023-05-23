package com.salazar.lordhosting.server.ui.backups

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteBackupDialog(
    openDialog: Boolean,
    onDelete: () -> Unit,
    onClose: () -> Unit,
) {
    if (!openDialog)
        return

    if (openDialog) {
        AlertDialog(
            onDismissRequest = onClose,
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        onClose()
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClose,
                ) {
                    Text("Cancel")
                }
            },
            title = {
                Text(
                    text = "Delete Backup?",
                )
            },
            text = {
                Text(
                    text = "This is a permanent operation. The backup cannot be recovered once deleted.",
                )
            }
        )
    }
}