package com.salazar.lordhosting.files.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.files.domain.models.File
import com.salazar.lordhosting.files.ui.dialogs.DeleteFileDialog
import com.salazar.lordhosting.files.ui.dialogs.FolderNameDialog

@Composable
fun FilesScreen(
    uiState: FilesUiState,
    onFilesUIAction: (FilesUIAction) -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            FilesTopBar(
                uiState = uiState,
                onFilesUIAction = onFilesUIAction,
                onCreateFolderClick = {
                    openDialog = true
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = it.calculateTopPadding()),
        ) {
            if (uiState.files.isEmpty())
                Text(
                    modifier = Modifier.fillMaxSize()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = "Empty folder.",
                )
            uiState.files.forEach { file ->
                File(
                    file = file,
                    readOnly = false,
                    onClick = {
                        if (!it.is_file)
                            onFilesUIAction(FilesUIAction.OnDirectoryClick(it.name))
                        else
                            onFilesUIAction(FilesUIAction.OnFileClick(it))
                    },
                    onFilesUIAction = onFilesUIAction,
                )
            }
        }
    }
    DeleteFileDialog(
        openDialog = uiState.openDeleteDialog,
        onDelete = {
            onFilesUIAction(FilesUIAction.OnDeleteFile)
        },
        onClose = {
            onFilesUIAction(FilesUIAction.OnOpenDeleteDialogChange(false))
        },
    )
    FolderNameDialog(
        openDialog = openDialog,
        onCreate = {
            onFilesUIAction(FilesUIAction.OnCreateFolder(it))
        },
        onClose = {
            openDialog = false
        }
    )
    BottomSheet(
        uiState = uiState,
        openBottomSheet = uiState.openBottomSheet,
        onFilesUIAction = onFilesUIAction,
    )
}

@Composable
fun BottomSheet(
    uiState: FilesUiState,
    openBottomSheet: Boolean,
    onFilesUIAction: (FilesUIAction) -> Unit,
) {
    if (!openBottomSheet)
        return

    ModalBottomSheet(
        onDismissRequest = {
            onFilesUIAction(FilesUIAction.OnOpenBottomSheetChange(false))
        },
        sheetState = uiState.bottomSheetState,
    ) {
        val file = uiState.selectedFile ?: return@ModalBottomSheet

        File(
            file = file,
            readOnly = true,
            onClick = {},
            onFilesUIAction = onFilesUIAction,
        )
        Divider()
        androidx.compose.material3.ListItem(
            modifier = Modifier
                .clickable {
                   onFilesUIAction(FilesUIAction.OnDeleteActionClick)
                },
            headlineContent = { Text("Delete") },
            leadingContent = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                )
            }
        )
    }
}

@Composable
fun File(
    file: File,
    readOnly: Boolean,
    onClick: (File) -> Unit,
    onFilesUIAction: (FilesUIAction) -> Unit,
) {
    val icon = when (file.is_file) {
        true -> Icons.Default.Description
        false -> Icons.Default.Folder
    }

    androidx.compose.material3.ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .let {
                if (!readOnly)
                    return@let it.clickable { onClick(file) }
                it
            },
        headlineContent = {
            Text(text = file.name)
        },
        leadingContent = {
            Icon(imageVector = icon, contentDescription = null)
        },
        trailingContent = {
            if (readOnly)
                return@ListItem
            Icon(
                modifier = Modifier.noRippleClickable {
                    onFilesUIAction(FilesUIAction.OnFileMoreClick(file))
                },
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
            )
        }
    )
}
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}