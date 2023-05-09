package com.salazar.lordhosting.server.ui.server

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.auth.data.interceptor.SetHeaderInterceptor
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

data class ServerUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val serverID: String = "",
    val server: Server? = null,
    val serverStats: WebSocketEvent.Stats? = null,
)

@HiltViewModel
class ServerViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val serverRepository: ServerRepository,
    private val setHeaderInterceptor: SetHeaderInterceptor,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(ServerUiState(isLoading = true))
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
            updateServerID(it)
            viewModelScope.launch {
                initWebSocket()
            }
        }
        viewModelScope.launch {
            serverRepository.getServer(serverID = serverID).collect(::updateServer)
        }
    }

    private fun updateServerID(serverID: String) {
        viewModelState.update {
            it.copy(serverID = serverID)
        }
    }

    private fun updateServer(server: Server) {
        viewModelState.update {
            it.copy(server = server)
        }
    }

    private fun updateServerStats(serverStats: WebSocketEvent.Stats) {
        viewModelState.update {
            it.copy(serverStats = serverStats)
        }
    }

    private suspend fun initWebSocket() = withContext(Dispatchers.IO) {
        serverRepository.getWebSocketData(serverUUID = serverID)
            .onSuccess { data ->
                print(data)
                val request = Request.Builder()
                    .url(data.socket)
                    .build()

                val client = OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .addInterceptor(setHeaderInterceptor)
                    .build()

                val webSocketListener = ConsoleWebSocketListener(data.token) { event ->
                    when(event) {
                        is WebSocketEvent.Stats -> updateServerStats(event)
                        else -> {}
                    }
                }
                client.newWebSocket(request, webSocketListener)
            }
    }

    fun updatePowerState(signal: String) {
        viewModelScope.launch {
            serverRepository.updatePowerState(
                serverID = serverID,
                signal = signal,
            )
        }
    }
}

