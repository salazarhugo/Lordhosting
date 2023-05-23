package com.salazar.lordhosting.server.domain.models;

data class Startup(
    val startup_command: String,
    val raw_startup_command: String,
    val variables: List<Variable>,
)

data class Variable(
    val name: String,
    val description: String,
    val env_variable: String,
    val default_value: String,
    val server_value: String,
    val is_editable: Boolean,
    val rules: String,
)
