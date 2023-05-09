package com.salazar.lordhosting.files.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.core.ui.components.LordButton
import com.salazar.lordhosting.core.ui.components.LordCard
import com.salazar.lordhosting.files.domain.models.File
import com.salazar.lordhosting.server.data.internal.bytesFormatter
import com.salazar.lordhosting.server.ui.server.StatusText
import com.salazar.lordhosting.ui.theme.LordBlue
import com.salazar.lordhosting.ui.theme.LordRed
import java.util.Locale

@Composable
fun FilesScreen(
    uiState: FilesUiState,
    onFilesUIAction: (FilesUIAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onFilesUIAction(FilesUIAction.OnBackPressed)
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                title = {
                    Text(text = "Files")
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = it.calculateTopPadding()),
        ) {
            uiState.files.forEach { file ->
                File(
                    file = file,
                    onClick = {
                        if (!it.is_file)
                            onFilesUIAction(FilesUIAction.OnDirectoryClick(it.name))
                    }
                )
            }
        }
    }
}

@Composable
fun File(
    file: File,
    onClick: (File) -> Unit,
) {
    val icon = when(file.is_file) {
        true -> Icons.Default.Description
        false -> Icons.Default.Folder
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(file) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Column() {
            Text(text = file.name)
//            Text(text = file.mimetype)
        }
    }
}