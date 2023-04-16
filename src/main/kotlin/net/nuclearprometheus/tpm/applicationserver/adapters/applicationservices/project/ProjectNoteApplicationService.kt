package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectNoteMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectNoteRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.note.NoteRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.note.NoteService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectNoteApplicationService(
    private val noteService: NoteService,
    private val noteRepository: NoteRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userContextProvider: UserContextProvider
) {

    private val logger = loggerFor(ProjectNoteApplicationService::class.java)

    fun getNotesForProject(projectId: UUID) = with(logger) {
        info("getNotesForProject($projectId)")

        singlePage(noteRepository.getAllByProjectId(ProjectId(projectId))).map { it.toView() }
    }

    fun createNote(projectId: UUID, request: ProjectNoteRequest.Create) = with(logger) {
        info("createNote($projectId, $request)")

        val user = userContextProvider.getCurrentUser()
        val teamMember = teamMemberRepository.getByUserIdAndProjectId(user.id, ProjectId(projectId))
            ?: throw IllegalArgumentException("User is not a member of the project")

        noteService.create(
            content = request.content,
            projectId = ProjectId(projectId),
            authorId = teamMember.id
        ).toView()
    }
}