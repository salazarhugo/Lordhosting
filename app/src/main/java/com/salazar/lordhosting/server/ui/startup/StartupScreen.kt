package com.salazar.lordhosting.server.ui.startup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.ui.components.LordCard
import com.salazar.lordhosting.server.domain.models.Variable
import com.salazar.lordhosting.server.ui.backups.BackupsUIAction

@Composable
fun StartupScreen(
    uiState: StartupUiState,
    onStartupUIAction: (BackupsUIAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onStartupUIAction(BackupsUIAction.OnBackPressed)
                        }
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.startup))
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
            StartupCommand(
                startupCommand = uiState.startup?.startup_command,
            )
            uiState.startup?.variables?.forEach { variable ->
                VariableCard(
                    variable = variable,
                )
            }
        }
    }
}

@Composable
fun VariableCard(
    variable: Variable,
) {
    LordCard() {
        Text(text = variable.name)
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
                .border(2.dp, Color.DarkGray, RoundedCornerShape(12.dp))
                .padding(12.dp)
            ,
        ) {
            Text(
                text = variable.server_value,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun StartupCommand(
    startupCommand: String?,
) {
    if (startupCommand == null)
        return
    LordCard() {
        Text(text = "Startup Command")
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