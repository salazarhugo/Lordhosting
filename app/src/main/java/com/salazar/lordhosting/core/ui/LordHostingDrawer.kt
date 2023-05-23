package com.salazar.lordhosting.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.Terminal
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.navigation.MainDestinations
import com.salazar.lordhosting.core.navigation.ServerDestinations
import kotlinx.coroutines.launch

data class Screen2(
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String? = null,
)

@Composable
fun LordHostingDrawer(
    drawerState: DrawerState,
    navBackStackEntry: NavBackStackEntry?,
    navController: NavController,
) {
    val serverItems = listOf(
        Screen2(
            route = ServerDestinations.SERVER_ROUTE,
            icon = Icons.Outlined.Terminal,
            selectedIcon = Icons.Rounded.Terminal,
            label = stringResource(id = R.string.console),
        ),
        Screen2(
            route = ServerDestinations.FILE_ROUTE,
            icon = Icons.Outlined.Dns,
            selectedIcon = Icons.Filled.Dns,
            label = stringResource(id = R.string.files),
        ),
        Screen2(
            route = ServerDestinations.USERS_ROUTE,
            icon = Icons.Outlined.People,
            selectedIcon = Icons.Default.People,
            label = stringResource(id = R.string.users)
        ),
        Screen2(
            route = ServerDestinations.BACKUPS_ROUTE,
            icon = Icons.Outlined.Backup,
            selectedIcon = Icons.Default.Backup,
            label = stringResource(id = R.string.backups)
        ),
        Screen2(
            route = ServerDestinations.STARTUP_ROUTE,
            icon = Icons.Outlined.ToggleOn,
            selectedIcon = Icons.Default.ToggleOn,
            label = stringResource(id = R.string.startup)
        ),
    )
    val selectedItem = remember { mutableStateOf(serverItems[0]) }
    val scope = rememberCoroutineScope()
    val serverID = navBackStackEntry?.arguments?.getString("serverID")

    ModalDrawerSheet {
        DrawerHeader()
        Spacer(Modifier.height(12.dp))
        DrawerMainSection(
            selectedItem = selectedItem.value,
            onScreen2Click = {
                navController.navigate(it.route)
                scope.launch {
                    drawerState.close()
                }
                selectedItem.value = it
            }
        )
        Divider(
            modifier = Modifier.padding(vertical = 16.dp),
        )
        if (serverID != null)
            serverItems.forEach { item ->
                NavigationDrawerItem(
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    },
                    label = { Text(item.label.toString()) },
                    selected = item == selectedItem.value,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedIconColor = Color.White,
                    ),
                    onClick = {
                        navController.navigate(item.route + "/" + serverID)
                        scope.launch {
                            drawerState.close()
                        }
                        selectedItem.value = item
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
    }
}

@Composable
fun DrawerHeader() {
    val image = when (isSystemInDarkTheme()) {
        true -> R.drawable.ldh
        false -> R.drawable.ldh
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
        )
    }
}

@Composable
fun DrawerMainSection(
    selectedItem: Screen2,
    onScreen2Click: (Screen2) -> Unit,
) {
    val mainScreen2s = listOf(
        Screen2(
            route = MainDestinations.SERVERS_ROUTE,
            icon = Icons.Outlined.Dns,
            selectedIcon = Icons.Rounded.Dns,
            label = stringResource(id = R.string.servers),
        ),
        Screen2(
            route = MainDestinations.HOME_ROUTE,
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
            label = stringResource(id = R.string.home),
        ),
        Screen2(
            route = MainDestinations.ACCOUNT_ROUTE,
            icon = Icons.Outlined.AccountCircle,
            selectedIcon = Icons.Default.AccountCircle,
            label = stringResource(id = R.string.account)
        ),
    )
    mainScreen2s.forEach { item ->
        NavigationDrawerItem(
            icon = {
                Icon(imageVector = item.icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
            },
            label = { Text(item.label.toString()) },
            selected = item == selectedItem,
            colors = NavigationDrawerItemDefaults.colors(
                unselectedIconColor = Color.White,
            ),
            onClick = {
                  onScreen2Click(item)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}
