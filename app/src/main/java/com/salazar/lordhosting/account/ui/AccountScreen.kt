package com.salazar.lordhosting.account.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.core.ui.components.LoadingScreen
import com.salazar.lordhosting.core.ui.components.LordCard

@Composable
fun AccountScreen(
    uiState: AccountUiState,
    onAccountUIAction: (AccountUIAction) -> Unit,
) {
    Scaffold() {
        val account = uiState.account
        if (account == null)
            LoadingScreen()
        else
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = it.calculateTopPadding())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                AccountHeaderCard(
                    username = account.username,
                    email = account.email,
                    onAccountUIAction = onAccountUIAction,
                )
                UpdatePasswordCard(
                    currentPassword = uiState.currentPassword,
                    newPassword = uiState.newPassword,
                    onAccountUIAction = onAccountUIAction,
                )
                UpdateEmailCard(
                    email = uiState.email,
                    currentPassword = uiState.currentPassword,
                    onAccountUIAction = onAccountUIAction,
                )
            }
    }
}

@Composable
fun AccountHeaderCard(
    username: String,
    email: String,
    onAccountUIAction: (AccountUIAction) -> Unit,
) {
    LordCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column() {
                Text(
                    text = username,
                )
                Text(
                    text = email,
                )
            }
            SignOutButton(
                onClick = {
                    onAccountUIAction(AccountUIAction.OnSignOut)
                }
            )
        }
    }
}

@Composable
fun SignOutButton(
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
    ) {
        Text(
            text = "Logout",
        )
    }
}