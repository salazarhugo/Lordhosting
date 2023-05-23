package com.salazar.lordhosting.files.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteFileDialog(
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
                    text = "Delete Files",
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete files? This is a permanent action and the files cannot be recovered.",
                )
            }
        )
    }
}