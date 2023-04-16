package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses

import java.time.ZonedDateTime
import java.util.*

sealed class NoteResponse {
    data class View(
        val id: UUID,
        val content: String,
        val author: NoteAuthorView,
        val createdAt: ZonedDateTime,
        val projectId: UUID
    ) : NoteResponse() {

        data class NoteAuthorView(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }
}