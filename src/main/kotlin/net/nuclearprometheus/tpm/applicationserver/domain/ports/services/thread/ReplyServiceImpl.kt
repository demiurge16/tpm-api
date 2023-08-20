package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class ReplyServiceImpl(
    private val repository: ReplyRepository,
    private val replyLikeRepository: ReplyLikeRepository,
    private val replyDislikeRepository: ReplyDislikeRepository,
    private val threadRepository: ThreadRepository,
    private val userRepository: UserRepository,
    private val logger: Logger
) : ReplyService {
    override fun create(content: String, authorId: UserId, threadId: ThreadId, parentReplyId: ReplyId?): Reply {
        logger.info("Creating reply with content: $content, authorId: $authorId, threadId: $threadId, parentReplyId: $parentReplyId")

        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val thread = threadRepository.get(threadId) ?: throw NotFoundException("Thread with id $threadId not found")

        val reply = Reply(
            content = content,
            author = author,
            threadId = thread.id,
            parentReplyId = parentReplyId
        );

        return repository.create(reply)
    }

    override fun update(id: ReplyId, content: String): Reply {
        logger.info("Updating reply with id $id with content: $content")

        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        reply.update(content)

        return repository.update(reply)
    }

    override fun like(id: ReplyId, authorId: UserId): Reply {
        logger.info("Adding like to reply with id $id")

        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val like = ReplyLike(author = author, replyId = reply.id)

        if (replyLikeRepository.getByReplyIdAndUserId(reply.id, author.id) != null) {
            return reply
        }
        replyDislikeRepository.deleteByReplyIdAndUserId(reply.id, author.id)
        replyLikeRepository.create(like)

        reply.addLike(like)
        return reply
    }

    override fun unlike(id: ReplyId, authorId: UserId): Reply {
        logger.info("Removing like from reply with id $id")

        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val like = ReplyLike(author = author, replyId = reply.id)

        if (replyLikeRepository.getByReplyIdAndUserId(reply.id, author.id) == null) {
            return reply
        }
        replyLikeRepository.deleteByReplyIdAndUserId(reply.id, author.id)

        reply.removeLike(like)
        return reply
    }

    override fun dislike(id: ReplyId, authorId: UserId): Reply {
        logger.info("Adding dislike to reply with id $id")

        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val dislike = ReplyDislike(author = author, replyId = reply.id)

        if (replyDislikeRepository.getByReplyIdAndUserId(reply.id, author.id) != null) {
            return reply
        }
        replyLikeRepository.deleteByReplyIdAndUserId(reply.id, author.id)
        replyDislikeRepository.create(dislike)

        reply.addDislike(dislike)
        return reply
    }

    override fun undislike(id: ReplyId, authorId: UserId): Reply {
        logger.info("Removing dislike from reply with id $id")

        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        val author = userRepository.get(authorId) ?: throw NotFoundException("Author with id $authorId not found")
        val dislike = ReplyDislike(author = author, replyId = reply.id)

        if (replyDislikeRepository.getByReplyIdAndUserId(reply.id, author.id) == null) {
            return reply
        }
        replyDislikeRepository.deleteByReplyIdAndUserId(reply.id, author.id)

        reply.removeDislike(dislike)
        return reply
    }

    override fun delete(id: ReplyId) {
        logger.info("Deleting reply with id $id")

        val reply = repository.get(id) ?: throw NotFoundException("Reply with id $id not found")
        reply.delete()
        repository.update(reply)
    }
}
