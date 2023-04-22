package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses.Author
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses.NoteResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note

object NoteMapper {

    fun Note.toView() = NoteResponse.Note(
        id = id.value,
        content = content,
        author = Author(
            teamMemberId = author.id.value,
            userId = author.user.id.value,
            firstName = author.user.firstName,
            lastName = author.user.lastName,
            email = author.user.email,
        ),
        createdAt = createdAt,
        projectId = projectId.value
    )
}