package com.salazar.lordhosting.files.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.files.data.repository.FilesRepository
import com.salazar.lordhosting.files.domain.models.File
import com.salazar.lordhosting.server.data.response.WebSocketEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditFileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val serverID: String = "",
    val serverStats: WebSocketEvent.Stats? = null,
    val selectedFile: File? = null,
    val content: String = "",
)

@HiltViewModel
class EditFileViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val filesRepository: FilesRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(EditFileUiState(isLoading = true))
    private lateinit var serverID: String
    private lateinit var file: String

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
        stateHandle.get<String>("file")?.let {
            file = it
        }
        viewModelScope.launch {
            val result = filesRepository.getFileContent(
                serverID = serverID,
                file = file,
            )
            result.onSuccess { content ->
                updateContent(content)
            }
        }
    }

    fun updateContent(content: String) {
        viewModelState.update {
            it.copy(content = content)
        }
    }

    fun saveChanges(onComplete: () -> Unit) {
        val content = uiState.value.content
        viewModelScope.launch {
            filesRepository.writeFile(
                serverID = serverID,
                file = file,
                content = content,
            )
            onComplete()
        }
    }
}

