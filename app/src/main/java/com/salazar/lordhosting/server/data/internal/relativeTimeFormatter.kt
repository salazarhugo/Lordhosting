package com.salazar.lordhosting.server.data.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun bytesFormatter(
    bytes: Long,
): AnnotatedString {
    return buildAnnotatedString {

        val units = arrayOf("B", "KiB", "MiB", "GiB", "TiB", "PiB")
        var size = bytes.toDouble()
        var unitIndex = 0
        while (size >= 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }

        val res = "%.1f %s".format(size, units[unitIndex])
        append(res)
    }
}
