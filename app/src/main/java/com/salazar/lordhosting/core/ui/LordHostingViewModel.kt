package com.salazar.lordhosting.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class LordHostingUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val searchInput: String = "",
)

@HiltViewModel
class LordHostingViewModel @Inject constructor(
//    private val userRepository: UserRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(LordHostingUiState(isLoading = true))

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init { }

//    private fun updateUser(user: User) {
//        viewModelState.update {
//            it.copy(user = user, isLoading = false)
//        }
//    }
}

