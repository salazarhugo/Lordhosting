package com.salazar.lordhosting.users.ui

import com.salazar.lordhosting.users.domain.models.User

sealed class UsersUIAction {
    object OnBackPressed : UsersUIAction()
    object OnNewUserClick : UsersUIAction()
    data class OnDeleteUser(val user: User) : UsersUIAction()
}
