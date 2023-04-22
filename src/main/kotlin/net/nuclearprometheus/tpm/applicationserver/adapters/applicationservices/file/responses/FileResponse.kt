package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses

import java.io.InputStream
import java.time.ZonedDateTime
import java.util.*

sealed class FileResponse {

    data class File(
        val id: UUID,
        val name: String,
        val uploadTime: ZonedDateTime,
        val uploader: Uploader,
        val projectId: UUID
    ) : FileResponse()

    data class Download(
        val name: String,
        val inputStream: InputStream
    ) : FileResponse()
}