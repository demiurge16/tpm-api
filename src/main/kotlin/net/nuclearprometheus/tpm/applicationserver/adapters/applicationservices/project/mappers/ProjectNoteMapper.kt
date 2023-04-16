package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectNoteResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note

object ProjectNoteMapper {
    fun Note.toView() = ProjectNoteResponse.View(
        id = id.value,
        content = content,
        author = ProjectNoteResponse.View.NoteAuthorView(
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