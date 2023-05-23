package com.salazar.lordhosting.core.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetPermissionsResponse(
    val attributes: PermissionAttributes,
)

@JsonClass(generateAdapter = true)
data class PermissionAttributes(
    val permissions: Map<String, PermissionResponse>
)

@JsonClass(generateAdapter = true)
data class PermissionResponse(
    val description: String,
    val keys: Map<String, String>,
)
