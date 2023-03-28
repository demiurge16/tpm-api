package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.note

import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.NoteId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

class NoteServiceImpl : NoteService {
    override fun getAll(): List<Note> {
        TODO("Not yet implemented")
    }

    override fun get(id: NoteId): Note? {
        TODO("Not yet implemented")
    }

    override fun create(content: String, authorId: UserId, projectId: ProjectId): Note {
        TODO("Not yet implemented")
    }

    override fun delete(id: NoteId) {
        TODO("Not yet implemented")
    }
}