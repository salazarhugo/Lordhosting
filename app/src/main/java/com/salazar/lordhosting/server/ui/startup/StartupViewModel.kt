package com.salazar.lordhosting.server.ui.startup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.server.data.repository.ServerRepository
import com.salazar.lordhosting.server.domain.models.Server
import com.salazar.lordhosting.server.domain.models.Startup
import com.salazar.lordhosting.users.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StartupUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val users: List<User> = emptyList(),
    val server: Server? = null,
    val startup: Startup? = null,
)

@HiltViewModel
class StartupViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val serverRepository: ServerRepository,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(StartupUiState(isLoading = true))
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
            serverRepository.getServer(serverID).collect(::updateServer)
        }
        viewModelScope.launch {
            val result = serverRepository.getStartup(serverID)
            result.onSuccess {
                updateStartup(it)
            }
        }
    }

    private fun updateServer(server: Server?) {
        viewModelState.update {
            it.copy(server = server)
        }
    }

    private fun updateStartup(startup: Startup) {
        viewModelState.update {
            it.copy(startup = startup)
        }
    }
}