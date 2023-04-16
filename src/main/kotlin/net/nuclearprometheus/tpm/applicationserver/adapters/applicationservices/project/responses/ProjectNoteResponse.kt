package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.time.ZonedDateTime
import java.util.*

sealed class ProjectNoteResponse {
    data class View(
        val id: UUID,
        val content: String,
        val author: NoteAuthorView,
        val createdAt: ZonedDateTime,
        val projectId: UUID
    ) : ProjectNoteResponse() {

        data class NoteAuthorView(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }
}