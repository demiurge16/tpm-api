package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.note

import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.NoteId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface NoteService {
    fun create(content: String, authorId: TeamMemberId, projectId: ProjectId): Note
    fun delete(id: NoteId)
}