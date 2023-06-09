package com.salazar.lordhosting.core.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun LordButton(
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White
    ),
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier,
        colors = colors,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        enabled = enabled,
    ) {
        Text(
            text = text,
        )
    }
}