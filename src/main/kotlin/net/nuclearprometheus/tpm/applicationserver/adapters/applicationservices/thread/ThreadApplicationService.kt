package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toNewDislike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toNewLike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toTag
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.ThreadRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ThreadApplicationService(
    private val service: ThreadService,
    private val repository: ThreadRepository,
    private val projectRepository: ProjectRepository,
    private val userContextProvider: UserContextProvider,
    private val teamMemberRepository: TeamMemberRepository
) {

    private val logger = loggerFor(ThreadApplicationService::class.java)

    fun getThreads(): Pageable<ThreadResponse.View> {
        logger.info("getThreads()")

        return singlePage(repository.getAll()).map {
            val project = projectRepository.get(it.projectId)
                ?: throw IllegalStateException("Project with id ${it.projectId} not found")
            it.toView(project)
        }
    }

    fun getThread(id: UUID): ThreadResponse.View {
        logger.info("getThread($id)")

        val thread = repository.get(ThreadId(id)) ?: throw NotFoundException("Thread with id $id not found")
        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")

        return thread.toView(project)
    }

    fun updateThread(id: UUID, request: ThreadRequest.Update): ThreadResponse.View {
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

    fun addLike(id: UUID): ThreadResponse.NewLike {
        logger.info("addLike($id)")

        val thread = repository.get(ThreadId(id)) ?: throw NotFoundException("Thread with id $id not found")
        val user = userContextProvider.getCurrentUser()
        teamMemberRepository.getByUserIdAndProjectId(user.id, thread.projectId)
            ?: throw IllegalStateException("User with id ${user.id} is not a member of project with id ${thread.projectId}")
        val updatedThread = service.addLike(ThreadId(id), user.id)

        return updatedThread.toNewLike(user)
    }

    fun addDislike(id: UUID): ThreadResponse.NewDislike {
        logger.info("addDislike($id)")

        val thread = repository.get(ThreadId(id)) ?: throw NotFoundException("Thread not found")
        val user = userContextProvider.getCurrentUser()
        teamMemberRepository.getByUserIdAndProjectId(user.id, thread.projectId)
            ?: throw IllegalStateException("Team member not found")
        val updatedThread = service.addDislike(ThreadId(id), user.id)

        return updatedThread.toNewDislike(user)
    }
}