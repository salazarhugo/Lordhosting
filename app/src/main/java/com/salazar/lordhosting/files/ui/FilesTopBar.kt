package com.salazar.lordhosting.files.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun FilesTopBar(
    uiState: FilesUiState,
    onFilesUIAction: (FilesUIAction) -> Unit,
    onCreateFolderClick: () -> Unit,
) {
    val isRoot = uiState.currentRoute.isEmpty()

    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    onFilesUIAction(FilesUIAction.OnBackPressed)
                }
            ) {
                AnimatedContent(targetState = isRoot, label = "") { isRoot ->
                    val icon = when (isRoot) {
                        true -> Icons.Default.Menu
                        false -> Icons.Default.ArrowBack
                    }
                    Icon(icon, contentDescription = null)
                }
            }
        },
        title = {
            Text(text = "Files")
        },
        actions = {
            IconButton(
                onClick = {},
            ) {
                Icon(Icons.Default.UploadFile, contentDescription = null)
            }
            IconButton(
                onClick = onCreateFolderClick,
            ) {
                Icon(Icons.Default.CreateNewFolder, contentDescription = null)
            }
        }
    )
}
