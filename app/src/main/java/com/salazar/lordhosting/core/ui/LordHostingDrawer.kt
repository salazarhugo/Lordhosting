package com.salazar.lordhosting.core.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.Terminal
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.navigation.MainDestinations
import com.salazar.lordhosting.core.navigation.ServerDestinations
import kotlinx.coroutines.launch

@Composable
fun LordHostingDrawer(
    drawerState: DrawerState,
    navBackStackEntry: NavBackStackEntry?,
    navController: NavController,
) {
    val serverItems = listOf(
        Screen(
            route = ServerDestinations.SERVER_ROUTE,
            icon = { Icon(Icons.Outlined.Terminal, null) },
            selectedIcon = { Icon(Icons.Rounded.Terminal, null) },
            label = stringResource(id = R.string.console),
        ),
        Screen(
            route = ServerDestinations.FILE_ROUTE,
            icon = { Icon(Icons.Outlined.Dns, null) },
            selectedIcon = { Icon(Icons.Filled.Dns, null) },
            label = stringResource(id = R.string.files),
        ),
        Screen(
            route = ServerDestinations.USERS_ROUTE,
            icon = { Icon(Icons.Outlined.People, null) },
            selectedIcon = { Icon(Icons.Default.People, null) },
            label = stringResource(id = R.string.users)
        ),
        Screen(
            route = ServerDestinations.BACKUPS_ROUTE,
            icon = { Icon(Icons.Outlined.People, null) },
            selectedIcon = { Icon(Icons.Default.People, null) },
            label = stringResource(id = R.string.backups)
        ),
        Screen(
            route = ServerDestinations.STARTUP_ROUTE,
            icon = { Icon(Icons.Outlined.People, null) },
            selectedIcon = { Icon(Icons.Default.People, null) },
            label = stringResource(id = R.string.startup)
        ),
    )
    val selectedItem = remember { mutableStateOf(serverItems[0]) }
    val scope = rememberCoroutineScope()
    val serverID = navBackStackEntry?.arguments?.getString("serverID")

    ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        if (serverID != null)
            serverItems.forEach { item ->
                NavigationDrawerItem(
                    icon = item.icon,
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