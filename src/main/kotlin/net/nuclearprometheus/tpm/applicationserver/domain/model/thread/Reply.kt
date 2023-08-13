package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import java.time.ZonedDateTime

class Reply(
    id: ReplyId = ReplyId(),
    content: String,
    author: User,
    createdAt: ZonedDateTime = ZonedDateTime.now(),
    threadLikes: List<ReplyLike> = mutableListOf(),
    threadDislikes: List<ReplyDislike> = mutableListOf(),
    parentReplyId: ReplyId? = null,
    threadId: ThreadId
) : Entity<ReplyId>(id) {
    var content = content; private set
    var author = author; private set
    var createdAt = createdAt; private set
    var threadLikes = threadLikes; private set
    var threadDislikes = threadDislikes; private set
    var parentReplyId = parentReplyId; private set
    var threadId = threadId; private set

    fun update(content: String) {
        this.content = content
    }

    fun addLike(like: ReplyLike) {
        threadLikes = threadLikes + like
    }

    fun addDislike(dislike: ReplyDislike) {
        threadDislikes = threadDislikes + dislike
    }
}