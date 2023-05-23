package com.salazar.lordhosting.server.ui.backups

import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.server.data.repository.ServerRepository
import com.salazar.lordhosting.server.domain.models.Backup
import com.salazar.lordhosting.server.domain.models.Server
import com.salazar.lordhosting.users.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BackupsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val users: List<User> = emptyList(),
    val backups: List<Backup> = emptyList(),
    val selectedBackup: Backup? = null,
    val bottomSheetState: SheetState = SheetState(skipPartiallyExpanded = false),
    val openBottomSheet: Boolean = false,
)

@HiltViewModel
class BackupsViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val serverRepository: ServerRepository,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(BackupsUiState(isLoading = false))
    private lateinit var serverID: String

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init {
        stateHandle.get<String>("serverID")?.let {
            serverID = it
        }
        viewModelScope.launch {
            val result = serverRepository.listBackups(serverID)
            result.onSuccess {
                updateBackups(it)
            }
        }
    }

    fun updateOpenBottomSheet(openBottomSheet: Boolean) {
        viewModelState.update {
            it.copy(openBottomSheet = openBottomSheet)
        }
    }

    fun updateSelectedBackups(backup: Backup) {
        viewModelState.update {
            it.copy(selectedBackup = backup)
        }
    }

    private fun updateBackups(backups: List<Backup>) {
        viewModelState.update {
            it.copy(backups = backups)
        }
    }

    private fun deleteBackup(backupID: String) {
        viewModelState.update {
            it.copy(backups = it.backups.filter { it.uuid != backupID })
        }
    }

    private fun addBackups(backup: Backup) {
        viewModelState.update {
            it.copy(backups = it.backups + backup)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        viewModelState.update {
            it.copy(isLoading = isLoading)
        }
    }

    fun createBackup() {
        viewModelScope.launch {
            updateIsLoading(true)
            serverRepository.createBackup(serverID).onSuccess {
                addBackups(it)
            }
            updateIsLoading(false)
        }
    }

    fun deleteBackup(onComplete: () -> Unit) {
        val backup = uiState.value.selectedBackup ?: return
        viewModelScope.launch {
            serverRepository.deleteBackup(
                serverID = serverID,
                backupID = backup.uuid,
            ).onSuccess {
                deleteBackup(backup.uuid)
            }
            onComplete()
        }
    }
}