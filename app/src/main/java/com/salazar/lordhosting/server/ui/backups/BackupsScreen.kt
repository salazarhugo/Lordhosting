package com.salazar.lordhosting.server.ui.backups

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.ui.components.ButtonWithLoading
import com.salazar.lordhosting.core.ui.components.LordCard
import com.salazar.lordhosting.server.domain.models.Backup

@Composable
fun BackupsScreen(
    uiState: BackupsUiState,
    onBackupsUIAction: (BackupsUIAction) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackupsUIAction(BackupsUIAction.OnBackPressed)
                        }
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.backups))
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = it.calculateTopPadding())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            ButtonWithLoading(
                text = "Create Backup",
                isLoading = uiState.isLoading,
                onClick = {
                    onBackupsUIAction(BackupsUIAction.OnCreateBackup)
                },
            )
            uiState.backups.forEach { backup ->
                BackupCard(
                    backup = backup,
                    onClick = {
                        onBackupsUIAction(BackupsUIAction.OnSelectBackup(backup))
                        onBackupsUIAction(BackupsUIAction.OnOpenBottomSheetChange(true))
                    }
                )
            }
        }
    }
    if (uiState.openBottomSheet)
        ModalBottomSheet(
            onDismissRequest = {
                onBackupsUIAction(BackupsUIAction.OnOpenBottomSheetChange(false))
           },
            sheetState = uiState.bottomSheetState,
        ) {
            BackupActionButtons(
                onBackupsUIAction = onBackupsUIAction,
            )
        }
}

@Composable
fun BackupActionButtons(
    onBackupsUIAction: (BackupsUIAction) -> Unit,
) {
    Column {
        ListItem(
            modifier = Modifier
                .clickable { onBackupsUIAction(BackupsUIAction.OnDelete) },
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
fun BackupCard(
    backup: Backup,
    onClick: () -> Unit,
) {
    LordCard(
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Default.Archive, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Column(
            ) {
                Text(text = backup.name)
                Text(
                    text = backup.checksum,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Composable
fun BackupsCommand(
    startupCommand: String?,
) {
    if (startupCommand == null)
        return
    LordCard() {
        Text(text = "Backups Command")
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
                .border(2.dp, Color.DarkGray, RoundedCornerShape(12.dp))
                .padding(12.dp)
            ,
        ) {
            Text(
                text = startupCommand,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}