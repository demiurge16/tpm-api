package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Reply as ReplyResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

object ReplyMapper {

    fun Reply.toView() = ReplyResponse(
        id = id.value,
        content = content,
        author = author.toView(),
        createdAt = createdAt,
        deleted = deleted,
        parentReplyId = parentReplyId?.value,
        threadId = threadId.value,
        likes = replyLikes.map {
            Like(
                id = it.id.value,
                createdAt = it.createdAt,
                author = author.toView()
            )
        },
        dislikes = replyDislikes.map {
            Dislike(
                id = it.id.value,
                createdAt = it.createdAt,
                author = author.toView()
            )
        }
    )

    fun Reply.toNewLike(author: User) = replyLikes.first { it.author.id == author.id }.let {
        ReplyNewLike(
            id = it.id.value,
            createdAt = it.createdAt,
            author = author.toView()
        )
    }

    fun Reply.toLikeRemoved(author: User) = ReplyLikeRemoved(
        id = id.value,
        createdAt = createdAt,
        author = author.toView()
    )

    fun Reply.toNewDislike(author: User) = replyDislikes.first { it.author.id == author.id }.let {
        ReplyNewDislike(
            id = it.id.value,
            createdAt = it.createdAt,
            author = author.toView()
        )
    }

    fun Reply.toDislikeRemoved(author: User) = ReplyDislikeRemoved(
        id = id.value,
        createdAt = createdAt,
        author = author.toView()
    )
}