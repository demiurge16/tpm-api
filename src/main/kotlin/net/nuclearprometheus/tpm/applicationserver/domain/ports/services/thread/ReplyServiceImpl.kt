package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.thread.ReplyAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.thread.ThreadAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.thread.ThreadStatusException
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import java.lang.IllegalStateException

class ReplyServiceImpl(
    private val repository: ReplyRepository,
    private val replyLikeRepository: ReplyLikeRepository,
    private val replyDislikeRepository: ReplyDislikeRepository,
    private val threadRepository: ThreadRepository,
    private val projectRepository: ProjectRepository,
    private val userContextProvider: UserContextProvider,
    private val logger: Logger
) : ReplyService {

    override fun create(content: String, threadId: ThreadId, parentReplyId: ReplyId?): Reply {
        logger.info("Creating reply with content: $content, threadId: $threadId, parentReplyId: $parentReplyId")

        val currentUser = userContextProvider.getCurrentUser()
        val thread = threadRepository.get(threadId) ?: throw NotFoundException("Thread with id $threadId not found")

        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread with id $threadId has status ${thread.status.title} and cannot be replied to")
        }

        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        val reply = Reply(
            content = content,
            author = currentUser,
            threadId = thread.id,
            parentReplyId = parentReplyId
        );

        return repository.create(reply)
    }

    override fun update(id: ReplyId, content: String): Reply {
        logger.info("Updating reply with id $id with content: $content")

        val currentUser = userContextProvider.getCurrentUser()
        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")

        if (reply.author.id != currentUser.id || !currentUser.hasRole(UserRole.ADMIN)) {
            throw ThreadAccessException("User ${currentUser.id} cannot update reply ${reply.id}")
        }

        reply.update(content)

        return repository.update(reply)
    }

    override fun like(id: ReplyId): Reply {
        logger.info("Adding like to reply with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        val thread = threadRepository.get(reply.threadId) ?: throw IllegalStateException("Thread with id ${reply.threadId} not found")

        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread with id ${thread.id} has status ${thread.status.title} and replies on it cannot be liked")
        }

        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${project.id} and cannot like replies on related threads")
        }

        val like = ReplyLike(author = currentUser, replyId = reply.id)

        if (replyLikeRepository.getByReplyIdAndUserId(reply.id, currentUser.id) != null) {
            logger.info("User ${currentUser.id} already liked reply ${reply.id}")
            return reply
        }
        replyDislikeRepository.deleteByReplyIdAndUserId(reply.id, currentUser.id)
        replyLikeRepository.create(like)

        reply.addLike(like)
        return reply
    }

    override fun unlike(id: ReplyId): Reply {
        logger.info("Removing like from reply with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        val thread = threadRepository.get(reply.threadId) ?: throw IllegalStateException("Thread with id ${reply.threadId} not found")

        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread with id ${thread.id} has status ${thread.status.title} and replies on it cannot be unliked")
        }

        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${project.id} and cannot unlike replies on related threads")
        }

        val like = ReplyLike(author = currentUser, replyId = reply.id)

        if (replyLikeRepository.getByReplyIdAndUserId(reply.id, currentUser.id) == null) {
            logger.info("User ${currentUser.id} did not like reply ${reply.id}")
            return reply
        }
        replyLikeRepository.deleteByReplyIdAndUserId(reply.id, currentUser.id)

        reply.removeLike(like)
        return reply
    }

    override fun dislike(id: ReplyId): Reply {
        logger.info("Adding dislike to reply with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        val thread = threadRepository.get(reply.threadId) ?: throw IllegalStateException("Thread with id ${reply.threadId} not found")

        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread with id ${thread.id} has status ${thread.status.title} and replies on it cannot be disliked")
        }

        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${project.id} and cannot dislike replies on related threads")
        }

        val dislike = ReplyDislike(author = currentUser, replyId = reply.id)

        if (replyDislikeRepository.getByReplyIdAndUserId(reply.id, currentUser.id) != null) {
            logger.info("User ${currentUser.id} already disliked reply ${reply.id}")
            return reply
        }
        replyLikeRepository.deleteByReplyIdAndUserId(reply.id, currentUser.id)
        replyDislikeRepository.create(dislike)

        reply.addDislike(dislike)
        return reply
    }

    override fun undislike(id: ReplyId): Reply {
        logger.info("Removing dislike from reply with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        val thread = threadRepository.get(reply.threadId) ?: throw IllegalStateException("Thread with id ${reply.threadId} not found")

        if (thread.status != ThreadStatus.ACTIVE) {
            throw ThreadStatusException("Thread with id ${thread.id} has status ${thread.status.title} and replies on it cannot be undisliked")
        }

        val project = projectRepository.get(thread.projectId) ?: throw IllegalStateException("Project with id ${thread.projectId} not found")
        if (!project.hasTeamMember(currentUser.id)) {
            throw ThreadAccessException("User ${currentUser.id} is not a member of project ${project.id} and cannot undislike replies on related threads")
        }

        val dislike = ReplyDislike(author = currentUser, replyId = reply.id)

        if (replyDislikeRepository.getByReplyIdAndUserId(reply.id, currentUser.id) == null) {
            logger.info("User ${currentUser.id} did not dislike reply ${reply.id}")
            return reply
        }
        replyDislikeRepository.deleteByReplyIdAndUserId(reply.id, currentUser.id)

        reply.removeDislike(dislike)
        return reply
    }

    override fun delete(id: ReplyId) {
        logger.info("Deleting reply with id $id")

        val currentUser = userContextProvider.getCurrentUser()
        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")

        if (reply.author.id != currentUser.id || !currentUser.hasRole(UserRole.ADMIN)) {
            throw ReplyAccessException("User ${currentUser.id} cannot delete reply ${reply.id}")
        }

        reply.delete()
        repository.update(reply)
    }
}
