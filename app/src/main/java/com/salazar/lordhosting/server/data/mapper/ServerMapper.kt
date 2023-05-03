package com.salazar.lordhosting.server.data.mapper

import com.salazar.lordhosting.server.data.response.ServerResponse
import com.salazar.lordhosting.server.domain.models.Server

fun ServerResponse.toServer(): Server {
    attributes.apply {
        return Server(
            name  =  name,
            description = description,
            identifier = identifier,
            node = node,
            uuid = uuid,
            ip = sftp_details.ip,
            port = sftp_details.port,
        )
    }
}
