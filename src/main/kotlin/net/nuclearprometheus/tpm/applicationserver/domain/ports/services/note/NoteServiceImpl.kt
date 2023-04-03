package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.note

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.NoteId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.note.NoteRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class NoteServiceImpl(
    private val noteRepository: NoteRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val projectRepository: ProjectRepository,
    private val logger: Logger
) : NoteService {

    override fun create(content: String, authorId: TeamMemberId, projectId: ProjectId): Note {
        teamMemberRepository.get(authorId) ?: throw NotFoundException("Author does not exist")
        projectRepository.get(projectId) ?: throw NotFoundException("Project does not exist")

        val note = Note(
            content = content,
            authorId = authorId,
            projectId = projectId
        )

        return noteRepository.create(note)
    }

    override fun delete(id: NoteId) {
        noteRepository.delete(id)
    }
}