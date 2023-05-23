package com.salazar.lordhosting.users.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.ui.components.ButtonWithLoading
import com.salazar.lordhosting.core.ui.components.LordButton
import com.salazar.lordhosting.core.ui.components.LordCard
import com.salazar.lordhosting.ui.theme.LordGreen
import com.salazar.lordhosting.users.domain.models.User

@Composable
fun UsersScreen(
    uiState: UsersUiState,
    onUsersUIAction: (UsersUIAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onUsersUIAction(UsersUIAction.OnBackPressed)
                        }
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.users))
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .padding(16.dp),
        ) {
            LordButton(
                text = "New User",
                modifier = Modifier.padding(vertical = 16.dp),
                enabled = !uiState.isLoading,
                onClick = {
                    onUsersUIAction(UsersUIAction.OnNewUserClick)
                },
            )
            if (uiState.users.isEmpty())
                Text(
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                    text = "It looks like you don't have any subusers.",
                )
            else
                UserList(
                    uiState = uiState,
                    onUsersUIAction = onUsersUIAction,
                )
        }
    }
}

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    uiState: UsersUiState,
    onUsersUIAction: (UsersUIAction) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        val users = uiState.users
        items(users) { user ->
            UserItem(
                user = user,
                onUsersUIAction = onUsersUIAction,
            )
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onUsersUIAction: (UsersUIAction) -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }

    LordCard(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.image,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user.email,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (user.twoFactorEnabled)
                Icon(Icons.Default.Lock, contentDescription = null, tint = LordGreen)
            else
                Icon(
                    Icons.Default.LockOpen,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            Text(
                text = user.permissionCount.toString() + " permissions",
            )
            IconButton(
                onClick = {
                    openDialog = true
                },
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        }
    }
    DeleteUserDialog(
        openDialog = openDialog,
        onDelete = {
            onUsersUIAction(UsersUIAction.OnDeleteUser(user))
        },
        onClose = {
            openDialog = false
        },
    )
}
