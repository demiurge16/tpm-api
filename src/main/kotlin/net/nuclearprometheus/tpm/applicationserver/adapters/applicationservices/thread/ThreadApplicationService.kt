package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toDislikeRemoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toLikeRemoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toNewDislike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toNewLike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Thread as ThreadResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.UpdateThread
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadDislikeRemoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadLikeRemoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadNewDislike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadNewLike
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ThreadApplicationService(
    private val service: ThreadService,
    private val repository: ThreadRepository,
    private val projectRepository: ProjectRepository,
    private val userContextProvider: UserContextProvider,
    private val teamMemberRepository: TeamMemberRepository,
    private val specificationBuilder: SpecificationBuilder<Thread>
) {

    private val logger = loggerFor(ThreadApplicationService::class.java)

    fun getThreads(query: FilteredRequest<Thread>): Page<ThreadResponse> {
        logger.info("getThreads($query)")

        return repository.get(query.toQuery(specificationBuilder))
            .map {
                val project = projectRepository.get(it.projectId)
                    ?: throw IllegalStateException("Project with id ${it.projectId} not found")
                it.toView(project)
            }
    }

    fun getThread(id: UUID): ThreadResponse {
        logger.info("getThread($id)")

        val thread = repository.get(ThreadId(id)) ?: throw NotFoundException("Thread with id $id not found")
        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")

        return thread.toView(project)
    }

    fun updateThread(id: UUID, request: UpdateThread): ThreadResponse {
        logger.info("updateThread($id, $request)")

        val updatedThread = service.update(
            id = ThreadId(id),
            title = request.title,
            content = request.content,
            tags = request.tags
        )

        val project = projectRepository.get(updatedThread.projectId) ?: throw IllegalStateException("Project with id ${updatedThread.projectId} not found")
        return updatedThread.toView(project)
    }

    fun addLike(id: UUID): ThreadNewLike {
        logger.info("addLike($id)")

        val thread = repository.get(ThreadId(id)) ?: throw NotFoundException("Thread with id $id not found")
        val user = userContextProvider.getCurrentUser()
        teamMemberRepository.getByUserIdAndProjectId(user.id, thread.projectId)
            ?: throw IllegalStateException("User with id ${user.id} is not a member of project with id ${thread.projectId}")

        return service.addLike(ThreadId(id)).toNewLike(user)
    }

    fun removeLike(id: UUID): ThreadLikeRemoved {
        logger.info("removeLike($id)")

        val thread = repository.get(ThreadId(id)) ?: throw NotFoundException("Thread not found")
        val user = userContextProvider.getCurrentUser()
        teamMemberRepository.getByUserIdAndProjectId(user.id, thread.projectId)
            ?: throw IllegalStateException("Team member not found")
        val updatedThread = service.removeLike(ThreadId(id))

        return updatedThread.toLikeRemoved(user)
    }

    fun addDislike(id: UUID): ThreadNewDislike {
        logger.info("addDislike($id)")

        val thread = repository.get(ThreadId(id)) ?: throw NotFoundException("Thread not found")
        val user = userContextProvider.getCurrentUser()
        teamMemberRepository.getByUserIdAndProjectId(user.id, thread.projectId)
            ?: throw IllegalStateException("Team member not found")
        val updatedThread = service.addDislike(ThreadId(id))

        return updatedThread.toNewDislike(user)
    }

    fun removeDislike(id: UUID): ThreadDislikeRemoved {
        logger.info("removeDislike($id)")

        val thread = repository.get(ThreadId(id)) ?: throw NotFoundException("Thread not found")
        val user = userContextProvider.getCurrentUser()
        teamMemberRepository.getByUserIdAndProjectId(user.id, thread.projectId)
            ?: throw IllegalStateException("Team member not found")
        val updatedThread = service.removeDislike(ThreadId(id))

        return updatedThread.toDislikeRemoved(user)
    }
}