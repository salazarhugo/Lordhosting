package com.salazar.lordhosting.account.data.mapper

import com.salazar.lordhosting.account.data.response.AccountDetailsResponse
import com.salazar.lordhosting.account.domain.models.Account

fun AccountDetailsResponse.toAccount(): Account {
    attributes.apply {
        return Account(
            id = id,
            username = username,
            name = "$first_name $last_name",
            email = email,
        )
    }
}