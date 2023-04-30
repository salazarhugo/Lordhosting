package com.salazar.lordhosting.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.salazar.lordhosting.core.navigation.LordHostingNavGraph
import com.salazar.lordhosting.ui.theme.LordHostingTheme


@Composable
fun LordHostingApp(
    appState: LordHostingAppState = rememberLordHostingAppState()
) {
    val viewModel = hiltViewModel<LordHostingViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val darkTheme = isSystemInDarkTheme()

    SetStatusBars(darkTheme = darkTheme)

    LordHostingTheme(darkTheme = darkTheme) {
        LordHostingNavGraph(
            appState = appState,
            uiState = uiState,
        )
    }
}

@Composable
fun SetStatusBars(
    darkTheme: Boolean,
) {
    val systemUiController = rememberSystemUiController()
    val darkIcons = !darkTheme

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = darkIcons,
        )
    }
}
