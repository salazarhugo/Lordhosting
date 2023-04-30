package com.salazar.lordhosting.server.ui.console

import android.provider.SyncStateContract
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.server.data.repository.ServerRepository
import com.salazar.lordhosting.server.data.response.Server
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
import okhttp3.internal.concurrent.Task
import java.time.Instant
import javax.inject.Inject

data class ConsoleUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val servers: List<Server> = emptyList(),
)

@HiltViewModel
class ConsoleViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val serverRepository: ServerRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(ConsoleUiState(isLoading = true))
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
        }
    }

    private fun updateConsole(servers: List<Server>) {
        viewModelState.update {
            it.copy(servers = servers)
        }
    }

    private suspend fun initWebSocket() = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("")
            .build()

        val client = OkHttpClient()

//        client.newWebSocket(request, chatWebSocketListener)
    }
}

