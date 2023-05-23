package com.salazar.lordhosting.core.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.navigation.MainDestinations

data class Screen(
    val route: String,
    val icon: @Composable () -> Unit,
    val selectedIcon: @Composable () -> Unit,
    val label: String? = null,
)

@Composable
fun LordHostingBottomBar(
    picture: String,
    currentRoute: String,
    onNavigate: (String) -> Unit,
) {
    val items = listOf(
        Screen(
            route = MainDestinations.SERVERS_ROUTE,
            icon = { Icon(Icons.Outlined.Dns, null, tint = MaterialTheme.colorScheme.onBackground) },
            selectedIcon = { Icon(Icons.Rounded.Dns, null, tint = MaterialTheme.colorScheme.onBackground) },
            label = stringResource(id = R.string.servers),
        ),
        Screen(
            route = MainDestinations.HOME_ROUTE,
            icon = { Icon(Icons.Outlined.Home, null, tint = MaterialTheme.colorScheme.onBackground) },
            selectedIcon = { Icon(Icons.Filled.Home, null, tint = MaterialTheme.colorScheme.onBackground) },
            label = stringResource(id = R.string.home),
        ),
        Screen(
            route = MainDestinations.ACCOUNT_ROUTE,
            icon = { Icon(Icons.Outlined.AccountCircle, null, tint = MaterialTheme.colorScheme.onBackground) },
            selectedIcon = { Icon(Icons.Default.AccountCircle, null, tint = MaterialTheme.colorScheme.onBackground) },
            label = stringResource(id = R.string.account)
        ),
    )

    CompositionLocalProvider(
//        LocalRippleTheme provides ClearRippleTheme
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background.compositeOver(Color.White),
            modifier = Modifier
                .navigationBarsPadding()
                .height(52.dp),
            windowInsets = BottomAppBarDefaults.windowInsets,
            tonalElevation = 0.dp,
        ) {
            items.forEachIndexed { index, screen ->
                NavigationBarItem(
                    icon = {
                        val icon =
                            if (currentRoute == screen.route) screen.selectedIcon else screen.icon
                        icon()
                    },
                    selected = currentRoute == screen.route,
                    onClick = {onNavigate(screen.route)},
                )
            }
        }
    }
}
