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

@Composable
fun UpdateEmailCard(
    email: String,
    currentPassword: String,
    enabled: Boolean = true,
    onAccountUIAction: (AccountUIAction) -> Unit,
) {
    LordCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        LordCardTitle(
            text = "Update email",
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            placeholder = {
                Text(text = "Email")
            },
            onValueChange = {
                onAccountUIAction(AccountUIAction.OnEmailChange(it))
            },
            enabled = enabled,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = currentPassword,
            placeholder = {
                Text(text = "Confirm Password")
            },
            onValueChange = {
                onAccountUIAction(AccountUIAction.OnNewPasswordChange(it))
            },
            enabled = enabled,
        )
        Spacer(Modifier.height(16.dp))
        LordButton(
            text = "Update Email",
            onClick = {
                onAccountUIAction(AccountUIAction.OnUpdateEmailClick)
            },
            enabled = email.isNotBlank() && currentPassword.isNotBlank(),
        )
    }
}
