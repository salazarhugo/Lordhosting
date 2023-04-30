package com.salazar.lordhosting.account.ui

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
import java.time.Instant
import javax.inject.Inject

data class AccountUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val account: Account? = null,
    val currentPassword: String = "",
    val newPassword: String = "",
    val email: String = "",
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(AccountUiState(isLoading = true))

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init {
        viewModelScope.launch {
            val res = accountRepository.getAccountDetails()
            res.onSuccess {
                updateAccount(it)
            }
        }
    }

    fun updateCurrentPassword(currentPassword: String) {
        viewModelState.update {
            it.copy(currentPassword = currentPassword)
        }
    }

    fun updateEmail(email: String) {
        viewModelState.update {
            it.copy(email = email)
        }
    }

    fun updateNewPassword(newPassword: String) {
        viewModelState.update {
            it.copy(newPassword = newPassword)
        }
    }

    private fun updateAccount(account: Account?) {
        viewModelState.update {
            it.copy(account = account)
        }
    }

    fun signOut(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = authRepository.logout()
            result.onSuccess { onSuccess() }
        }
    }

    fun onUpdatePasswordClick() {
        val state = uiState.value
        viewModelScope.launch {
            accountRepository.updatePassword(
                currentPassword = state.currentPassword,
                newPassword = state.newPassword,
            )
        }
    }

    fun onUpdateEmailClick() {
        val state = uiState.value
        viewModelScope.launch {
            accountRepository.updateEmail(
                email = state.email,
                password = state.currentPassword,
            )
        }
    }
}

