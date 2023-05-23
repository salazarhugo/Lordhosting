package com.salazar.lordhosting.users.ui.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.data.response.PermissionResponse
import com.salazar.lordhosting.core.ui.components.LordTextField

@Composable
fun CreateUserScreen(
    uiState: CreateUserUiState,
    onCreateUserUIAction: (CreateUserUIAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onCreateUserUIAction(CreateUserUIAction.OnBackPressed)
                        }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.create_user))
                },
                actions = {
                    val enabled = uiState.email.isNotBlank() && uiState.selectedPermissions.isNotEmpty()
                    Button(
                        onClick = {
                            onCreateUserUIAction(CreateUserUIAction.OnCreateClick)
                        },
                        enabled = enabled,
                    ) {
                        Text(
                            text = stringResource(id = R.string.create),
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding()),
        ) {
            LordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(text = stringResource(id = R.string.user_email))
                },
                value = uiState.email,
                onValueChange = { email ->
                    onCreateUserUIAction(CreateUserUIAction.OnEmailChange(email))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            PermissionList(
                permissions = uiState.permissions,
                selectedPermissions = uiState.selectedPermissions,
                onCreateUserUIAction = onCreateUserUIAction,
            )
        }
    }
}

@Composable
fun PermissionList(
    selectedPermissions: Set<String>,
    permissions: Map<String, PermissionResponse>,
    onCreateUserUIAction: (CreateUserUIAction) -> Unit,
) {
    LazyColumn() {
        permissions.forEach { (title, permission) ->
            item {
                Permission(
                    permission = permission,
                    group = title,
                    selectedPermissions = selectedPermissions,
                    onCreateUserUIAction = onCreateUserUIAction,
                )
            }
        }
    }
}

@Composable
fun Permission(
    selectedPermissions: Set<String>,
    group: String,
    permission: PermissionResponse,
    onCreateUserUIAction: (CreateUserUIAction) -> Unit,
) {
    permission.keys.forEach { (name, description) ->
        val selected = selectedPermissions.contains("$group.$name")
        ListItem(
            modifier = Modifier
                .clickable {
                    onCreateUserUIAction(CreateUserUIAction.OnTogglePermission("$group.$name"))
                },
            headlineContent = {
                Text(
                    text = name.uppercase(),
                )
            },
            supportingContent = {
                Text(
                    text = description,
                    style = MaterialTheme.typography.labelMedium,
                )
            },
            leadingContent = {
                Checkbox(
                    checked = selected,
                    onCheckedChange = {
                        onCreateUserUIAction(CreateUserUIAction.OnTogglePermission("$group.$name"))
                    },
                )
            }
        )
    }
}
