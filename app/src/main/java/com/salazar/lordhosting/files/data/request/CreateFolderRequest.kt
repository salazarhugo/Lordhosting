package com.salazar.lordhosting.files.data.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateFolderRequest(
    val root: String,
    val name: String,
)
