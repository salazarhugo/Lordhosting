package com.salazar.lordhosting.files.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun FolderNameDialog(
    openDialog: Boolean,
    onCreate: (String) -> Unit,
    onClose: () -> Unit,
) {
    if (!openDialog)
        return
    var folderName by remember { mutableStateOf("") }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = onClose,
            confirmButton = {
                TextButton(
                    onClick = {
                        onCreate(folderName)
                        onClose()
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClose,
                ) {
                    Text("Dismiss")
                }
            },
            text = {
                TextField(
                    value = folderName,
                    onValueChange = {
                        folderName = it
                    },
                    placeholder = {
                        Text(text = "Folder name")
                    }
                )
            }
        )
    }
}