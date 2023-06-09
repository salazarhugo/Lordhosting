package com.salazar.lordhosting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.salazar.lordhosting.core.ui.LordHostingApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val a= mutableListOf("Fwf")
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            LordHostingApp()
        }
    }
}