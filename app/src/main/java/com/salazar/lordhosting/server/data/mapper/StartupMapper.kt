package com.salazar.lordhosting.server.data.mapper

import com.salazar.lordhosting.server.data.response.ListVariablesResponse
import com.salazar.lordhosting.server.data.response.VariableResponse
import com.salazar.lordhosting.server.domain.models.Startup
import com.salazar.lordhosting.server.domain.models.Variable

fun ListVariablesResponse.toStartup(): Startup {
    return Startup(
        raw_startup_command = meta.raw_startup_command,
        startup_command = meta.startup_command,
        variables = data.map { it.toVariable() },
    )
}

fun VariableResponse.toVariable(): Variable {
    attributes.apply {
        return Variable(
            name = name,
            description = description,
            env_variable = env_variable,
            default_value = default_value,
            rules = rules,
            is_editable = is_editable,
            server_value = server_value,
        )
    }
}
