package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.time.ZonedDateTime
import java.util.*

sealed class ProjectFileResponse {

    data class View(
        val id: UUID,
        val name: String,
        val uploadTime: ZonedDateTime,
        val uploader: UploaderView,
        val projectId: UUID
    ) : ProjectFileResponse() {

        data class UploaderView(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }
}