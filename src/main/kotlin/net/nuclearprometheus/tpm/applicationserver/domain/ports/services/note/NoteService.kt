package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.note

import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.NoteId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface NoteService {

    fun getAll(): List<Note>
    fun get(id: NoteId): Note?
    fun create(
        content: String,
        authorId: UserId,
        projectId: ProjectId
    ): Note
    fun delete(id: NoteId)
}