package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Author
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Dislike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Like
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ReplyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply

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
        parentReplyId = parentReplyId?.value,
        threadId = threadId.value,
        likes = threadLikes.map {
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
        dislikes = threadDislikes.map {
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

    fun Reply.toNewLike() = ReplyResponse.NewLike(
        id = id.value,
        createdAt = createdAt,
        author = Author(
            userId = author.id.value,
            firstName = author.firstName,
            lastName = author.lastName,
            email = author.email
        )
    )

    fun Reply.toNewDislike() = ReplyResponse.NewDislike(
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