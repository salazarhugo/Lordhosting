package com.salazar.lordhosting.users.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteUserDialog(
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
                    text = "Delete this subuser?",
                )
            },
            text = {
                Text(
                    text = "Are you sure you wish to remove this subuser? They will have all access to this server revoked immediately.",
                )
            }
        )
    }
}