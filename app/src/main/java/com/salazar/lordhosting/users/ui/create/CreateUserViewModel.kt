package com.salazar.lordhosting.users.ui.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.core.data.response.PermissionResponse
import com.salazar.lordhosting.users.data.repository.UserRepository
import com.salazar.lordhosting.users.data.repository.UserRepositoryImpl
import com.salazar.lordhosting.users.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateUserUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val users: List<User> = emptyList(),
    val selectedUser: User? = null,
    val openDeleteDialog: Boolean = false,
    val permissions: Map<String, PermissionResponse> = emptyMap(),
    val email: String = "",
    val selectedPermissions: Set<String> = emptySet(),
)

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(CreateUserUiState(isLoading = true))
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
        refreshPermissions()
    }

    private fun refreshPermissions() {
        viewModelScope.launch {
            userRepository.getPermissions().onSuccess {
                updatePermissions(it)
            }
        }
    }

    fun updateEmail(email: String) {
        viewModelState.update {
            it.copy(email = email)
        }
    }

    private fun updatePermissions(permissions: Map<String, PermissionResponse>) {
        viewModelState.update {
            it.copy(permissions = permissions)
        }
    }

    fun updateSelectedUser(user: User) {
        viewModelState.update {
            it.copy(selectedUser = user)
        }
    }

    fun onCreateClick(onComplete: () -> Unit) {
        val state = uiState.value
        val email = state.email

        viewModelScope.launch {
            userRepository.createUser(
                serverID = serverID,
                email = email,
                permissions = state.selectedPermissions.toList(),
            )
            onComplete()
        }
    }

    fun togglePermission(
        permission: String,
    ) {
        viewModelState.update {
            val new = if (it.selectedPermissions.contains(permission))
                it.selectedPermissions.minus(permission)
            else
                it.selectedPermissions.plus(permission)
            it.copy(selectedPermissions = new)
        }
    }
}

