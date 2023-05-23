package com.salazar.lordhosting.files.data.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteFileRequest(
    val root: String,
    val files: List<String>,
)
