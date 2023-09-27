package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.thread.ThreadAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.thread.ThreadStatusException
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider

class ThreadServiceImpl(
    private val repository: ThreadRepository,
    private val threadLikeRepository: ThreadLikeRepository,
    private val threadDislikeRepository: ThreadDislikeRepository,
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider,
    private val projectRepository: ProjectRepository,
    private val logger: Logger
) : ThreadService {

    override fun create(title: String, content: String, tags: List<String>, projectId: ProjectId): Thread {
        val currentUser = userContextProvider.getCurrentUser()
        logger.info("Creating thread with title: $title, content: $content, authorId: ${currentUser.id}, tags: $tags, projectId: $projectId")

        val author = userRepository.get(currentUser.id) ?: throw NotFoundException("Author with id ${currentUser.id} not found")
        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project with id $projectId not found")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMember(currentUser.id)) {
            throw ProjectAccessException("User ${currentUser.id} is not a member of project $projectId")
        }

        val threadId = ThreadId()
        val thread = Thread(
            id = threadId,
            title = title,
            content = content,
            author = author,
            tags = tags.map { Tag(name = it, threadId = threadId) },
            projectId = project.id
        );

        return repository.create(thread)
    }

    override fun update(id: ThreadId, title: String, content: String, tags: List<String>): Thread {
        logger.info("Updating thread with id $id with title: $title, content: $content, tags: $tags")

        val currentUser = userContextProvider.getCurrentUser()
        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")

        if (thread.author.id != currentUser.id && !currentUser.hasRole(UserRole.ADMIN)) {
            throw ThreadAccessException("User ${currentUser.id} cannot update thread $id")
        }

        thread.update(title, content, tags.map { Tag(name = it, threadId = id) })

        return repository.update(thread)
    }

    override fun addLike(id: ThreadId): Thread {
        logger.info("Adding like to thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread $id is has status ${thread.status.title} and therefore cannot be liked")
        }

        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${thread.projectId}")
        }

        val like = ThreadLike(author = currentUser, threadId = thread.id)

        if (threadLikeRepository.getByThreadIdAndAuthorId(thread.id, currentUser.id) != null) {
            logger.info("Thread with id $id already has like from author with id ${currentUser.id}")
            return thread
        }
        threadDislikeRepository.deleteByThreadIdAndAuthorId(thread.id, currentUser.id)
        threadLikeRepository.create(like)

        thread.addLike(like)
        return thread
    }

    override fun removeLike(id: ThreadId): Thread {
        logger.info("Removing like from thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread $id is has status ${thread.status.title} and therefore cannot be unliked")
        }

        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${thread.projectId}")
        }

        val like = ThreadLike(author = currentUser, threadId = thread.id)

        if (threadLikeRepository.getByThreadIdAndAuthorId(thread.id, currentUser.id) == null) {
            logger.info("Thread with id $id does not have like from author with id ${currentUser.id}")
            return thread
        }
        threadLikeRepository.deleteByThreadIdAndAuthorId(thread.id, currentUser.id)

        thread.removeLike(like)
        return thread
    }

    override fun addDislike(id: ThreadId): Thread {
        logger.info("Adding dislike to thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread $id is has status ${thread.status.title} and therefore cannot be disliked")
        }

        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${thread.projectId}")
        }

        val dislike = ThreadDislike(author = currentUser, threadId = thread.id)

        if (threadDislikeRepository.getByThreadIdAndAuthorId(thread.id, currentUser.id) != null) {
            logger.info("Thread with id $id already has dislike from author with id ${currentUser.id}")
            return thread
        }
        threadLikeRepository.deleteByThreadIdAndAuthorId(thread.id, currentUser.id)
        threadDislikeRepository.create(dislike)

        thread.addDislike(dislike)
        return thread
    }

    override fun removeDislike(id: ThreadId): Thread {
        logger.info("Removing dislike from thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread $id is has status ${thread.status.title} and therefore cannot be undisliked")
        }

        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${thread.projectId}")
        }

        val dislike = ThreadDislike(author = currentUser, threadId = thread.id)

        if (threadDislikeRepository.getByThreadIdAndAuthorId(thread.id, currentUser.id) == null) {
            logger.info("Thread with id $id does not have dislike from author with id ${currentUser.id}")
            return thread
        }
        threadDislikeRepository.deleteByThreadIdAndAuthorId(thread.id, currentUser.id)

        thread.removeDislike(dislike)
        return thread
    }

    override fun activate(id: ThreadId): Thread {
        logger.info("Activating thread with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.author.id != currentUser.id && !currentUser.hasRole(UserRole.ADMIN)) {
            throw ThreadAccessException("User ${currentUser.id} cannot change status of thread $id")
        }

        thread.activate()

        return repository.update(thread)
    }

    override fun freeze(id: ThreadId): Thread {
        logger.info("Freezing thread with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.author.id != currentUser.id && !currentUser.hasRole(UserRole.ADMIN)) {
            throw ThreadAccessException("User ${currentUser.id} cannot change status of thread $id")
        }

        thread.freeze()

        return repository.update(thread)
    }

    override fun close(id: ThreadId): Thread {
        logger.info("Closing thread with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.author.id != currentUser.id && !currentUser.hasRole(UserRole.ADMIN)) {
            throw ThreadAccessException("User ${currentUser.id} cannot change status of thread $id")
        }

        thread.close()

        return repository.update(thread)
    }

    override fun archive(id: ThreadId): Thread {
        logger.info("Archiving thread with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.author.id != currentUser.id && !currentUser.hasRole(UserRole.ADMIN)) {
            throw ThreadAccessException("User ${currentUser.id} cannot change status of thread $id")
        }

        thread.archive()

        return repository.update(thread)
    }

    override fun delete(id: ThreadId): Thread {
        logger.info("Deleting thread with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        if (thread.author.id != currentUser.id && !currentUser.hasRole(UserRole.ADMIN)) {
            throw ThreadAccessException("User ${currentUser.id} cannot change status of thread $id")
        }

        thread.delete()

        return repository.update(thread)
    }
}