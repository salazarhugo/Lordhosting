package com.salazar.lordhosting.server.ui.server

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.ui.components.LordCard
import com.salazar.lordhosting.server.data.internal.bytesFormatter
import java.util.Locale

@Composable
fun ServerScreen(
    uiState: ServerUiState,
    onServerUIAction: (ServerUIAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val image = when (isSystemInDarkTheme()) {
                        true -> R.drawable.ldh
                        false -> R.drawable.ldh
                    }
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .height(40.dp)
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = it.calculateTopPadding())
                .padding(16.dp),
        ) {
            ServerStats(
                uiState = uiState,
                onServerUIAction = onServerUIAction,
            )
        }
    }
}

@Composable
fun ServerStats(
    uiState: ServerUiState,
    onServerUIAction: (ServerUIAction) -> Unit,
) {
    val stats = uiState.serverStats ?: return

    FilledTonalIconButton(
        modifier = Modifier,
        onClick = {
          onServerUIAction(ServerUIAction.OnConsoleClick)
        },
    ) {
        Icon(Icons.Default.Terminal, contentDescription = null)
    }

    StatusText(
        modifier = Modifier.padding(16.dp),
        status = stats.state,
    )

    val server = uiState.server
    StatCard(
        icon = Icons.Default.Wifi,
        title = "Address",
        text = server?.ip ?: "",
    )
    StatCard(
        icon = Icons.Default.Memory,
        title = "CPU Load",
        text = stats.cpu_absolute.toString() + "% / 400",
    )
    StatCard(
        icon = Icons.Default.Memory,
        title = "Memory",
        text = bytesFormatter(bytes = stats.memory_bytes).text + " / " + bytesFormatter(bytes = stats.memory_limit_bytes).text,
    )
    StatCard(
        icon = Icons.Default.Storage,
        title = "Disk",
        text = bytesFormatter(bytes = stats.disk_bytes).text + " / 50 GiB",
    )
    StatCard(
        icon = Icons.Default.CloudDownload,
        title = "Network (Inbound)",
        text = bytesFormatter(bytes = stats.network.rx_bytes).text,
    )
    StatCard(
        icon = Icons.Default.CloudUpload,
        title = "Network (Outbound)",
        text = bytesFormatter(bytes = stats.network.tx_bytes).text,
    )
}

@Composable
fun StatCard(
    icon: ImageVector,
    title: String,
    text: String,
) {
    LordCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(48.dp),
                shadowElevation = 8.dp,
            ) {
                Icon(
                    modifier = Modifier.padding(12.dp),
                    imageVector = icon,
                    contentDescription = null,
                )
            }
            Column() {
                Text(
                    text = title,
                )
                Text(
                    text = text,
                )
            }
        }
    }
}

@Composable
fun StatusText(
    modifier: Modifier = Modifier,
    status: String,
) {
    val color = when (status) {
        "running" -> Color.Green
        "offline" -> Color.Red
        "starting" -> Color(0xFFFFA500)
        else -> Color.Gray
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            color = color,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.padding(end = 8.dp)
                .size(8.dp)
        ) {
            // This box will contain the colored dot
        }
        Text(
            text = status.capitalize(Locale.ROOT),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
