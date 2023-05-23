package com.salazar.lordhosting.files.ui

import androidx.compose.material3.SheetState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.auth.data.interceptor.SetHeaderInterceptor
import com.salazar.lordhosting.files.data.repository.FilesRepository
import com.salazar.lordhosting.files.data.response.FileResponse
import com.salazar.lordhosting.files.domain.models.File
import com.salazar.lordhosting.server.data.repository.ServerRepository
import com.salazar.lordhosting.server.data.response.ServerResponse
import com.salazar.lordhosting.server.data.response.WebSocketEvent
import com.salazar.lordhosting.server.data.websocket.ConsoleWebSocketListener
import com.salazar.lordhosting.server.domain.models.Server
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

data class FilesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val serverID: String = "",
    val serverStats: WebSocketEvent.Stats? = null,
    val files: List<File> = emptyList(),
    val currentRoute: List<String> = emptyList(),
    val openBottomSheet: Boolean = false,
    val bottomSheetState: SheetState = SheetState(false),
    val selectedFile: File? = null,
    val openDeleteDialog: Boolean = false,
)

@HiltViewModel
class FilesViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val filesRepository: FilesRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(FilesUiState(isLoading = true))
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
            updateFilesID(it)
        }
        viewModelScope.launch {
            val result = filesRepository.listFiles(serverID = serverID)
            result.onSuccess { files ->
                updateFiles(files)
            }
        }
    }

    private fun updateSelectedFile(file: File?) {
        viewModelState.update {
            it.copy(selectedFile = file)
        }
    }

    private fun updateCurrentRoute(currentRoute: List<String>) {
        viewModelState.update {
            it.copy(currentRoute = currentRoute)
        }
    }

    fun updateOpenDeleteDialog(openDeleteDialog: Boolean) {
        viewModelState.update {
            it.copy(openDeleteDialog = openDeleteDialog)
        }
    }

    private fun updateFiles(files: List<File>) {
        viewModelState.update {
            it.copy(files = files)
        }
    }

    private fun updateFilesID(serverID: String) {
        viewModelState.update {
            it.copy(serverID = serverID)
        }
    }

    fun onBackClick() {
        val route = uiState.value.currentRoute.toMutableList()
        route.removeLast()
        updateCurrentRoute(route)

        listFiles()
    }

    private fun listFiles() {
        val currentRoute = uiState.value.currentRoute.joinToString(separator = "/")
        viewModelScope.launch {
            val result = filesRepository.listFiles(
                serverID = serverID,
                directory = currentRoute,
            )
            result.onSuccess { files ->
                updateFiles(files)
            }
        }
    }

    fun onDirectoryClick(name: String) {
        val route = uiState.value.currentRoute.toMutableList()
        route.add(name)
        updateCurrentRoute(route)

        listFiles()
    }

    fun onCreateFolder(name: String) {
        val currentRoute = uiState.value.currentRoute.joinToString(separator = "/")
        viewModelScope.launch {
            filesRepository.createFolder(
                serverID = serverID,
                root = currentRoute,
                folderName = name,
            ).onSuccess {
                listFiles()
            }
        }
    }

    fun onFileClick(file: File) {
        updateSelectedFile(file = file)
        updateOpenSheet(openBottomSheet = true)
    }

    fun updateOpenSheet(openBottomSheet: Boolean) {
        viewModelState.update {
            it.copy(openBottomSheet = openBottomSheet)
        }
    }

    fun deleteFile() {
        val currentRoute = uiState.value.currentRoute.joinToString(separator = "/")
        val selectedFile = uiState.value.selectedFile ?: return
        viewModelScope.launch {
            filesRepository.deleteFiles(
                serverID = serverID,
                root = currentRoute,
                files = listOf(selectedFile.name),
            ).onSuccess {
                listFiles()
            }
        }
    }
}

