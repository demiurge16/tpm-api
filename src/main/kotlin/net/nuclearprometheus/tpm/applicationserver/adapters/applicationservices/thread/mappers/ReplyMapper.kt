package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Author
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Dislike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Like
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ReplyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

object ReplyMapper {

    fun Reply.toView() = ReplyResponse.View(
        id = id.value,
        content = content,
        author = Author(
            userId = author.id.value,
            firstName = author.firstName,
            lastName = author.lastName,
            email = author.email
        ),
        createdAt = createdAt,
        deleted = deleted,
        parentReplyId = parentReplyId?.value,
        threadId = threadId.value,
        likes = replyLikes.map {
            Like(
                id = it.id.value,
                createdAt = it.createdAt,
                author = Author(
                    userId = it.author.id.value,
                    firstName = it.author.firstName,
                    lastName = it.author.lastName,
                    email = it.author.email
                )
            )
        },
        dislikes = replyDislikes.map {
            Dislike(
                id = it.id.value,
                createdAt = it.createdAt,
                author = Author(
                    userId = it.author.id.value,
                    firstName = it.author.firstName,
                    lastName = it.author.lastName,
                    email = it.author.email
                )
            )
        }
    )

    fun Reply.toNewLike(author: User) = replyLikes.first { it.author.id == author.id }.let {
        ReplyResponse.NewLike(
            id = it.id.value,
            createdAt = it.createdAt,
            author = Author(
                userId = it.author.id.value,
                firstName = it.author.firstName,
                lastName = it.author.lastName,
                email = it.author.email
            )
        )
    }

    fun Reply.toLikeRemoved(author: User) = ReplyResponse.LikeRemoved(
        id = id.value,
        createdAt = createdAt,
        author = Author(
            userId = author.id.value,
            firstName = author.firstName,
            lastName = author.lastName,
            email = author.email
        )
    )

    fun Reply.toNewDislike(author: User) = replyDislikes.first { it.author.id == author.id }.let {
        ReplyResponse.NewDislike(
            id = it.id.value,
            createdAt = it.createdAt,
            author = Author(
                userId = it.author.id.value,
                firstName = it.author.firstName,
                lastName = it.author.lastName,
                email = it.author.email
            )
        )
    }

    fun Reply.toDislikeRemoved(author: User) = ReplyResponse.DislikeRemoved(
        id = id.value,
        createdAt = createdAt,
        author = Author(
            userId = author.id.value,
            firstName = author.firstName,
            lastName = author.lastName,
            email = author.email
        )
    )
}