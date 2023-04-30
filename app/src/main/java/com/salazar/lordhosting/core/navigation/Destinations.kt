package com.salazar.lordhosting.core.navigation

import androidx.navigation.NavHostController

/**
 * Destinations used in the [LordHostingApp].
 */
object LordHostingDestinations {
    const val ROOT_ROUTE = "root"
    const val AUTH_ROUTE = "auth"
    const val MAIN_ROUTE = "main"
    const val SERVER_ROUTE = "server"
    const val SETTING_ROUTE = "setting"
}

/**
 * Destinations used in the [ServerGraph].
 */
object ServerDestinations {
    const val CONSOLE_ROUTE = "console"
    const val FILE_ROUTE = "files"
    const val BACKUPS_ROUTE = "backups"
    const val USERS_ROUTE = "users"
    const val SETTINGS_ROUTE = "settings"
}
/**
 * Destinations used in the [MainGraph].
 */
object MainDestinations {
    const val SERVERS_ROUTE = "servers"
    const val ACCOUNT_ROUTE = "account"
    const val BILLING_ROUTE = "billing"
}

/**
 * Destinations used in the [AuthGraph].
 */
object AuthDestinations {
    const val SIGN_IN_ROUTE = "signIn"
}

/**
 * Destinations used in [Settings].
 */
object SettingDestinations {
    const val SETTINGS_ROUTE = "settings"
    const val THEME_ROUTE = "theme"
    const val NOTIFICATIONS_ROUTE = "notifications"
    const val LANGUAGE_ROUTE = "language"
    const val SECURITY_ROUTE = "security"
    const val PASSWORD_ROUTE = "password"
}

/**
 * Models the navigation actions in the app.
 */
class LordHostingNavigationActions(
    private val navController: NavHostController,
) {
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }

    val navigateToConsole: (String) -> Unit = { serverId ->
        navController.navigate("${ServerDestinations.CONSOLE_ROUTE}/$serverId") {
            launchSingleTop = true
        }
    }

    val navigateToPassword: (Boolean) -> Unit = { hasPassword ->
        navController.navigate("${SettingDestinations.PASSWORD_ROUTE}/$hasPassword") {
            launchSingleTop = true
        }
    }

    val navigateToSecurity = {
        navController.navigate(SettingDestinations.SECURITY_ROUTE) {
            launchSingleTop = true
        }
    }

    val navigateToLanguage: () -> Unit = {
        navController.navigate(SettingDestinations.LANGUAGE_ROUTE) {
            launchSingleTop = true
        }
    }

    val navigateToNotifications: () -> Unit = {
        navController.navigate(SettingDestinations.NOTIFICATIONS_ROUTE) {
            launchSingleTop = true
        }
    }

    val navigateToTheme: () -> Unit = {
        navController.navigate(SettingDestinations.THEME_ROUTE) {
            launchSingleTop = true
        }
    }

    val navigateToMain: () -> Unit = {
        navController.navigate(LordHostingDestinations.MAIN_ROUTE) {
            navController.popBackStack(route = LordHostingDestinations.MAIN_ROUTE, inclusive = true)
            launchSingleTop = true
        }
    }

    val navigateToSignIn: () -> Unit = {
        navController.navigate(AuthDestinations.SIGN_IN_ROUTE) {
            val a = navController.popBackStack(route = AuthDestinations.SIGN_IN_ROUTE, inclusive = true)
            print(a)
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSettings: () -> Unit = {
        navController.navigate(LordHostingDestinations.SETTING_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }
}