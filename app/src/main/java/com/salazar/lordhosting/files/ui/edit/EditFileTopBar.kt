package com.salazar.lordhosting.files.ui.edit

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.salazar.lordhosting.R

@Composable
fun EditFileTopBar(
    onEditFileUIAction: (EditFileUIAction) -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    onEditFileUIAction(EditFileUIAction.OnBackPressed)
                }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        title = {
            Text(text = "Edit file")
        },
        actions = {
            Button(
                onClick = {
                      onEditFileUIAction(EditFileUIAction.OnSaveChanges)
                },
            ) {
                Text(text = stringResource(id = R.string.save_changes))
            }
        }
    )
}
