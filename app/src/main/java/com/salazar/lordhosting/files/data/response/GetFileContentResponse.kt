package com.salazar.lordhosting.files.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetFileContentResponse(
    val data: String
)