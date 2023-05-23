package com.salazar.lordhosting.users.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.users.data.repository.UserRepository
import com.salazar.lordhosting.users.data.repository.UserRepositoryImpl
import com.salazar.lordhosting.users.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsersUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val users: List<User> = emptyList(),
    val selectedUser: User? = null,
    val openDeleteDialog: Boolean = false,
    val serverID: String = "",
)

@HiltViewModel
class UsersViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(UsersUiState(isLoading = false))
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
            viewModelState.update { it.copy(serverID = serverID) }
        }
        viewModelScope.launch {
            userRepository.listUsers(serverID).collect(::updateUsers)
        }
        refreshUsers()
    }

    private fun refreshUsers() {
        viewModelScope.launch {
            userRepository.fetchUsers(serverID).onSuccess {
                updateUsers(it)
            }
        }
    }

    fun updateOpenDeleteDialog(open: Boolean) {
        viewModelState.update {
            it.copy(openDeleteDialog = open)
        }
    }

    private fun updateUsers(users: List<User>) {
        viewModelState.update {
            it.copy(users = users)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(
                serverID = serverID,
                userID = user.uuid,
            ).onSuccess {
                refreshUsers()
            }
        }
    }

    fun updateSelectedUser(user: User) {
        viewModelState.update {
            it.copy(selectedUser = user)
        }
    }
}

