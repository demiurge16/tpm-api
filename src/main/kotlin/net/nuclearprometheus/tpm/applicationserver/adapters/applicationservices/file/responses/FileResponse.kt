package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses

import java.io.InputStream
import java.time.ZonedDateTime
import java.util.*

sealed class FileResponse {

    data class View(
        val id: UUID,
        val name: String,
        val uploadTime: ZonedDateTime,
        val uploader: UploaderView,
        val projectId: UUID
    ) : FileResponse() {

        data class UploaderView(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }

    data class Download(
        val name: String,
        val inputStream: InputStream
    ) : FileResponse()
}