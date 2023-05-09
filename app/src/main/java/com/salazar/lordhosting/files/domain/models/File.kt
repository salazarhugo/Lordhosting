package com.salazar.lordhosting.files.domain.models


data class File(
    val name: String,
    val mode: String,
    val size: Int,
    val is_file: Boolean,
    val is_symlink: Boolean,
    val is_editable: Boolean?,
    val mimetype: String,
)