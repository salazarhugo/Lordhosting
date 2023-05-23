package com.salazar.lordhosting.files.ui.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salazar.lordhosting.core.ui.components.LordTextField

@Composable
fun EditFileScreen(
    uiState: EditFileUiState,
    onEditFileUIAction: (EditFileUIAction) -> Unit,
) {
    Scaffold(
        topBar = {
            EditFileTopBar(
                onEditFileUIAction = onEditFileUIAction,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = it.calculateTopPadding()),
        ) {
            LordTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                value = uiState.content,
                onValueChange = {
                    onEditFileUIAction(EditFileUIAction.OnContentChange(it))
                }
            )
        }
    }
}