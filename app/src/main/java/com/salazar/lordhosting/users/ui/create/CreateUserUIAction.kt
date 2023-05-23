package com.salazar.lordhosting.users.ui.create

import com.salazar.lordhosting.core.data.response.PermissionResponse
import com.salazar.lordhosting.users.domain.models.User

sealed class CreateUserUIAction {
    object OnBackPressed : CreateUserUIAction()
    object OnNewUserClick : CreateUserUIAction()
    object OnCreateClick : CreateUserUIAction()
    data class OnDeleteUser(val user: User) : CreateUserUIAction()
    data class OnEmailChange(val email: String) : CreateUserUIAction()
    data class OnTogglePermission(
        val permission: String,
    ) : CreateUserUIAction()
}
