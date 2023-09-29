package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate
import java.time.ZonedDateTime

class Reply(
    id: ReplyId = ReplyId(),
    content: String,
    author: User,
    createdAt: ZonedDateTime = ZonedDateTime.now(),
    deleted: Boolean = false,
    replyLikes: List<ReplyLike> = mutableListOf(),
    replyDislikes: List<ReplyDislike> = mutableListOf(),
    parentReplyId: ReplyId? = null,
    threadId: ThreadId
) : Entity<ReplyId>(id) {

    init {
        validate {
            assert { content.isNotBlank() } otherwise {
                ValidationError("content", "Content cannot be blank")
            }
            assert { id != parentReplyId } otherwise {
                ValidationError("parentReplyId", "Reply cannot be parent of itself")
            }
        }
    }

    var content = content; private set
    var author = author; private set
    var createdAt = createdAt; private set
    var deleted = deleted; private set
    var replyLikes = replyLikes; private set
    var replyDislikes = replyDislikes; private set
    var parentReplyId = parentReplyId; private set
    var threadId = threadId; private set

    fun update(content: String) {
        validate {
            assert { content.isNotBlank() } otherwise {
                ValidationError("content", "Content cannot be blank")
            }
        }

        this.content = content
    }

    fun addLike(like: ReplyLike) {
        this.replyLikes += like
    }

    fun removeLike(like: ReplyLike) {
        this.replyLikes -= like
    }

    fun addDislike(dislike: ReplyDislike) {
        this.replyDislikes += dislike
    }

    fun removeDislike(dislike: ReplyDislike) {
        this.replyDislikes -= dislike
    }

    fun delete() {
        this.content = "<deleted>"
        this.replyLikes = emptyList()
        this.replyDislikes = emptyList()
        this.deleted = true
    }
}