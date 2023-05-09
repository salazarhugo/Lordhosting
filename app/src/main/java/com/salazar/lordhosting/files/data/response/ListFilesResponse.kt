package com.salazar.lordhosting.files.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListFilesResponse(
    val data: List<FileResponse>
)

@JsonClass(generateAdapter = true)
data class FileResponse(
    val attributes: FileAttributes,
)

@JsonClass(generateAdapter = true)
data class FileAttributes(
    val name: String,
    val mode: String,
    val size: Int,
    val is_file: Boolean,
    val is_symlink: Boolean,
    val is_editable: Boolean?,
    val mimetype: String,
)