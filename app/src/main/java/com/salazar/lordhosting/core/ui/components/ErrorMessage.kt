package com.salazar.lordhosting.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(
    errorMessage: String?,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    if (errorMessage == null || errorMessage.isBlank()) return

    Surface(
        modifier = Modifier.padding(paddingValues = paddingValues),
        color = MaterialTheme.colorScheme.errorContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = errorMessage,
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

