package com.salazar.lordhosting.server.data.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ListVariablesResponse(
    val data: List<VariableResponse>,
    val meta: VariablesMetaResponse,
)

@JsonClass(generateAdapter = true)
data class VariablesMetaResponse(
   val startup_command: String,
   val raw_startup_command: String,
)

@JsonClass(generateAdapter = true)
data class VariableResponse(
    val attributes: VariablesAttributes,
)

@JsonClass(generateAdapter = true)
data class VariablesAttributes(
    val name: String,
    val description: String,
    val env_variable: String,
    val default_value: String,
    val server_value: String,
    val is_editable: Boolean,
    val rules: String,
)