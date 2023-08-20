package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class ThreadServiceImpl(
    private val repository: ThreadRepository,
    private val threadLikeRepository: ThreadLikeRepository,
    private val threadDislikeRepository: ThreadDislikeRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val logger: Logger
) : ThreadService {

    override fun create(title: String, content: String, authorId: UserId, tags: List<String>, projectId: ProjectId): Thread {
        logger.info("Creating thread with title: $title, content: $content, authorId: $authorId, tags: $tags, projectId: $projectId")

        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        teamMemberRepository.getByUserIdAndProjectId(authorId, projectId)
            ?: throw NotFoundException("Author with id $authorId is not a member of project with id $projectId")
        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project with id $projectId not found")

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

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        thread.update(title, content, tags.map { Tag(name = it, threadId = id) })

        return repository.update(thread)
    }

    override fun addLike(id: ThreadId, authorId: UserId): Thread {
        logger.info("Adding like to thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val like = ThreadLike(author = author, threadId = thread.id)

        if (threadLikeRepository.getByThreadIdAndAuthorId(thread.id, author.id) != null) {
            return thread
        }
        threadDislikeRepository.deleteByThreadIdAndAuthorId(thread.id, author.id)
        threadLikeRepository.create(like)

        thread.addLike(like)
        return thread
    }

    override fun removeLike(id: ThreadId, authorId: UserId): Thread {
        logger.info("Removing like from thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val like = ThreadLike(author = author, threadId = thread.id)

        if (threadLikeRepository.getByThreadIdAndAuthorId(thread.id, author.id) == null) {
            return thread
        }
        threadLikeRepository.deleteByThreadIdAndAuthorId(thread.id, author.id)

        thread.removeLike(like)
        return thread
    }

    override fun addDislike(id: ThreadId, authorId: UserId): Thread {
        logger.info("Adding dislike to thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val dislike = ThreadDislike(author = author, threadId = thread.id)

        if (threadDislikeRepository.getByThreadIdAndAuthorId(thread.id, author.id) != null) {
            return thread
        }
        threadLikeRepository.deleteByThreadIdAndAuthorId(thread.id, author.id)
        threadDislikeRepository.create(dislike)

        thread.addDislike(dislike)
        return thread
    }

    override fun removeDislike(id: ThreadId, authorId: UserId): Thread {
        logger.info("Removing dislike from thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val dislike = ThreadDislike(author = author, threadId = thread.id)

        if (threadDislikeRepository.getByThreadIdAndAuthorId(thread.id, author.id) == null) {
            return thread
        }
        threadDislikeRepository.deleteByThreadIdAndAuthorId(thread.id, author.id)

        thread.removeDislike(dislike)
        return thread
    }

    override fun activate(id: ThreadId): Thread {
        logger.info("Activating thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        thread.activate()

        return repository.update(thread)
    }

    override fun freeze(id: ThreadId): Thread {
        logger.info("Freezing thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        thread.freeze()

        return repository.update(thread)
    }

    override fun close(id: ThreadId): Thread {
        logger.info("Closing thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        thread.close()

        return repository.update(thread)
    }

    override fun archive(id: ThreadId): Thread {
        logger.info("Archiving thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        thread.archive()

        return repository.update(thread)
    }

    override fun delete(id: ThreadId): Thread {
        logger.info("Deleting thread with id $id")

        val thread = repository.get(id) ?: throw NotFoundException("Thread with id $id not found")
        thread.delete()

        return repository.update(thread)
    }
}