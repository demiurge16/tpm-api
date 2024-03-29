package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.CreateThread
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Thread as ThreadResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.singlePage
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ProjectThreadApplicationService(
    private val service: ThreadService,
    private val repository: ThreadRepository,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(ProjectThreadApplicationService::class.java)

    fun getThreadsForProject(projectId: UUID): Page<ThreadResponse> {
        logger.info("GET /api/v1/project/$projectId/thread")
        val project = projectRepository.get(ProjectId(projectId))
            ?: throw IllegalStateException("Project with id $projectId not found")

        return singlePage(repository.getAllByProjectId(ProjectId(projectId))).map { it.toView(project) }
    }

    fun createThread(projectId: UUID, request: CreateThread) = with(logger) {
        info("POST /api/v1/project/$projectId/thread")

        service.create(
            title = request.title,
            content = request.content,
            projectId = ProjectId(projectId),
            tags = request.tags
        )
    }
}