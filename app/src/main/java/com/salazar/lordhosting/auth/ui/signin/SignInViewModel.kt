package com.salazar.lordhosting.auth.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lordhosting.auth.data.repository.AuthRepositoryImpl
import com.salazar.lordhosting.auth.domain.usecase.SignInWithApiKeyUseCase
import com.salazar.lordhosting.server.data.repository.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignInUiState(
    val isSignedIn: Boolean? = false,
    val isLoading: Boolean,
    val withApiKey: Boolean = false,
    val errorMessage: String? = null,
    val email: String = "",
    val password: String = "",
    val apiKey: String = "",
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithApiKeyUseCase: SignInWithApiKeyUseCase,
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val serverRepository: ServerRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(SignInUiState(isLoading = false))

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init {
        checkIfAlreadySignedIn()
    }

    private fun checkIfAlreadySignedIn() {
        viewModelScope.launch {
            val result = serverRepository.listServers().map {}
            updateIsSignedIn(isSignedIn = result.isSuccess)
        }
    }

    fun onPasswordChange(password: String) {
        viewModelState.update {
            it.copy(password = password)
        }
    }

    fun onWithApiKeyChange(withApiKey: Boolean) {
        viewModelState.update {
            it.copy(withApiKey = withApiKey)
        }
    }

    fun onEmailChange(email: String) {
        viewModelState.update {
            it.copy(email = email)
        }
    }

    private fun updateErrorMessage(errorMessage: String?) {
        viewModelState.update {
            it.copy(errorMessage = errorMessage, isLoading = false)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        viewModelState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun validateInput(
        email: String,
    ): Boolean {
        return email.isNotBlank()
    }

    fun onSignInClick() {
        updateIsLoading(true)
        val state = uiState.value
        state.apply {
            if (withApiKey)
                signInWithApiKey(
                    apiKey = apiKey,
                )
            else
                signInWithEmailPassword(
                    username = email,
                    password = password,
                )
        }
    }

    private fun signInWithApiKey(apiKey: String) {
        viewModelScope.launch {
            updateIsLoading(true)
            val result = signInWithApiKeyUseCase(apiKey)
            result.onSuccess {
                updateIsSignedIn(true)
            }
            result.onFailure {
                updateErrorMessage("Failed to login")
            }
            updateIsLoading(false)
        }
    }

    private fun signInWithEmailPassword(
        username: String,
        password: String,
    ) {
        val formattedPassword = password
            .replace("\n", "")
            .replace("\r", "")

        if (!validateInput(formattedPassword)) {
            updateErrorMessage("Password can't be empty")
            updateIsLoading(false)
            return
        }
        viewModelScope.launch {
            val result = authRepositoryImpl.login(
                username = username,
                password = formattedPassword,
            )
            result.onSuccess {
                updateIsSignedIn(true)
            }
            result.onFailure {
                updateErrorMessage("Failed to login")
            }
            updateIsLoading(false)
        }
    }

    private fun updateIsSignedIn(isSignedIn: Boolean) {
        viewModelState.update {
            it.copy(isSignedIn = isSignedIn)
        }
    }

    fun onApiKeyChange(apiKey: String) {
        viewModelState.update {
            it.copy(apiKey = apiKey)
        }
    }
}
