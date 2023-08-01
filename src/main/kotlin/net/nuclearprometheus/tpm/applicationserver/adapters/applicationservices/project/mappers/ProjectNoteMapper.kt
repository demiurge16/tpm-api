package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses.Author
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses.NoteResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses.ProjectShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.responses.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project

object ProjectNoteMapper {

    fun Note.toView(project: Project) = NoteResponse.Note(
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
        project = ProjectShortView(
            id = project.id.value,
            title = project.title,
            status = ProjectStatus(
                status = project.status,
                title = project.status.title,
                description = project.status.shortDescription
            )
        )
    )
}