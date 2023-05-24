package com.salazar.lordhosting.account.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.core.ui.components.LordButton
import com.salazar.lordhosting.core.ui.components.LordCard
import com.salazar.lordhosting.core.ui.components.LordCardTitle
import com.salazar.lordhosting.core.ui.components.LordTextField

@Composable
fun UpdatePasswordCard(
    currentPassword: String,
    newPassword: String,
    enabled: Boolean = true,
    onAccountUIAction: (AccountUIAction) -> Unit,
) {
    LordCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        LordCardTitle(
            text = "Update password",
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = currentPassword,
            placeholder = {
                Text(text = "Current Password")
            },
            onValueChange = {
                onAccountUIAction(AccountUIAction.OnCurrentPasswordChange(it))
            },
            enabled = enabled,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newPassword,
            placeholder = {
                Text(text = "New Password")
            },
            onValueChange = {
                onAccountUIAction(AccountUIAction.OnNewPasswordChange(it))
            },
            enabled = enabled,
        )
        Spacer(Modifier.height(16.dp))
        LordButton(
            text = "Update Password",
            onClick = {
                onAccountUIAction(AccountUIAction.OnUpdatePasswordClick)
            },
            enabled = newPassword.isNotBlank() && currentPassword.isNotBlank(),
        )
    }
}