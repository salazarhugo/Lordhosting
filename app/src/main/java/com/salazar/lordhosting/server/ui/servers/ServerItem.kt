package com.salazar.lordhosting.server.ui.servers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.core.ui.components.LordCard
import com.salazar.lordhosting.server.data.response.ServerResponse
import com.salazar.lordhosting.server.domain.models.Server

@Composable
fun ServerItem(
    modifier: Modifier = Modifier,
    server: Server,
    onClick: (Server) -> Unit,
) {
    LordCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(server) },
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FilledTonalIconButton(onClick = {}) {
                    Icon(Icons.Default.Dns, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = server.name,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Wifi,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${server.ip}:${server.port}",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}
