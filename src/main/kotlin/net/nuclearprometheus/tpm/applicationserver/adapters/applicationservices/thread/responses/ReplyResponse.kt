package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import java.time.ZonedDateTime
import java.util.UUID

sealed class ReplyResponse {

    data class View (
        val id: UUID,
        val content: String,
        val author: Author,
        val createdAt: ZonedDateTime,
        val parentReplyId: UUID?,
        val threadId: UUID,
        val likes: List<Like>,
        val dislikes: List<Dislike>
    ) : ReplyResponse()

    data class NewLike(
        val id: UUID,
        val author: Author,
        val createdAt: ZonedDateTime
    ) : ReplyResponse()

    data class NewDislike(
        val id: UUID,
        val author: Author,
        val createdAt: ZonedDateTime
    ) : ReplyResponse()
}