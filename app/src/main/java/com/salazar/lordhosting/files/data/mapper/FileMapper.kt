package com.salazar.lordhosting.files.data.mapper

import com.salazar.lordhosting.files.data.response.FileResponse
import com.salazar.lordhosting.files.domain.models.File

fun FileResponse.toFile(): File {
    attributes.apply {
        return File(
            name= name,
            mode= mode,
            size= size,
            is_file= is_file,
            is_symlink= is_symlink,
            is_editable= is_editable,
            mimetype= mimetype,
        )
    }
}