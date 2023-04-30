package com.salazar.lordhosting.server.ui.servers

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.salazar.lordhosting.R
import com.salazar.lordhosting.server.data.response.Server

@Composable
fun ServersScreen(
    uiState: ServersUiState,
    onServerClick: (Server) -> Unit,
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
        LazyColumn(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .padding(16.dp),
        ) {
            items(items = uiState.servers) { server ->
                ServerItem(
                    server = server,
                    onClick = onServerClick,
                )
            }
        }
    }
}
