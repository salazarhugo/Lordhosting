package com.salazar.lordhosting.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.account.data.repository.AccountRepository
import com.salazar.lordhosting.account.domain.models.Account
import com.salazar.lordhosting.auth.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val currentPassword: String = "",
    val newPassword: String = "",
    val email: String = "",
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(HomeUiState(isLoading = true))

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init {
        viewModelScope.launch {
        }
    }

    fun signOut(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = authRepository.logout()
            result.onSuccess { onSuccess() }
        }
    }
}

