package com.salazar.lordhosting.server.ui.servers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.server.data.repository.ServerRepository
import com.salazar.lordhosting.server.data.response.ServerResponse
import com.salazar.lordhosting.server.domain.models.Server
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ServersUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val servers: List<Server> = emptyList(),
)

@HiltViewModel
class ServersViewModel @Inject constructor(
    private val serverRepository: ServerRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(ServersUiState(isLoading = true))

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init {
        viewModelScope.launch {
            serverRepository.listServers()
                .onSuccess { updateServers(it) }
        }
    }

    private fun updateServers(servers: List<Server>) {
        viewModelState.update {
            it.copy(servers = servers)
        }
    }
}

