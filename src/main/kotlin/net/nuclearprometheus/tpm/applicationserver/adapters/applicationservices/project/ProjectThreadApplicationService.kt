package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectThreadRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.singlePage
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.util.*

@Service
class ProjectThreadApplicationService(
    private val service: ThreadService,
    private val repository: ThreadRepository,
    private val userContextProvider: UserContextProvider,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(ProjectThreadApplicationService::class.java)

    fun getThreadsForProject(projectId: UUID) = with(logger) {
        info("GET /api/v1/project/$projectId/thread")

        val project = projectRepository.get(ProjectId(projectId))
            ?: throw IllegalStateException("Project with id $projectId not found")
        singlePage(repository.getAllByProjectId(ProjectId(projectId))).map { it.toView(project) }
    }
    fun createThread(projectId: UUID, request: ProjectThreadRequest.Create) = with(logger) {
        info("POST /api/v1/project/$projectId/thread")

        val user = userContextProvider.getCurrentUser()
        service.create(
            title = request.title,
            content = request.content,
            projectId = ProjectId(projectId),
            authorId = user.id,
            tags = request.tags
        )
    }
}