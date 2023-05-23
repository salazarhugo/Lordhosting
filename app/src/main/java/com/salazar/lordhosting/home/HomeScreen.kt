package com.salazar.lordhosting.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.ui.components.LordButton
import com.salazar.lordhosting.core.ui.components.LordCard

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onHomeUIAction: (HomeUIAction) -> Unit,
) {
    val uriHandler = LocalUriHandler.current
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
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            val discordLink = stringResource(id = R.string.discord_link)
            val espaceClientLink = stringResource(id = R.string.espace_client_link)
            val supportLink = stringResource(id = R.string.support_link)
            HomeCard(
                title = "Discord",
                body = "Rejoignez notre discord !",
                buttonText = "Join Now!",
                onClick = {
                    uriHandler.openUri(discordLink)
                },
            )
            HomeCard(
                title = "Espace Client",
                body = "Join our discord server for faster support!",
                buttonText = "Visit Now!",
                onClick = {
                    uriHandler.openUri(espaceClientLink)
                },
            )
            HomeCard(
                title = "Support",
                body = "Join our discord server for faster support!",
                buttonText = "Get Support!",
                onClick = {
                    uriHandler.openUri(supportLink)
                },
            )
        }
    }
}

@Composable
fun HomeCard(
    title: String,
    body: String,
    buttonText: String,
    onClick: () -> Unit,
) {
    LordCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(Modifier.height(16.dp))
        LordButton(
            modifier = Modifier.fillMaxWidth(),
            text = buttonText,
            onClick = onClick,
        )
    }
}