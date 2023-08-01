package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses

import java.time.ZonedDateTime
import java.util.*

sealed class NoteResponse {
    data class Note(
        val id: UUID,
        val content: String,
        val author: Author,
        val createdAt: ZonedDateTime,
        val project: ProjectShortView
    ) : NoteResponse()
}