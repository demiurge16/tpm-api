package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import java.time.ZonedDateTime
import java.util.*

sealed class ThreadResponse {

    data class View(
        val id: UUID,
        val title: String,
        val content: String,
        val author: Author,
        val createdAt: ZonedDateTime,
        val project: ProjectShortView,
        val status: Status,
        val likes: List<Like>,
        val dislikes: List<Dislike>,
        val tags: List<Tag>
    ) : ThreadResponse()

    data class NewLike(
        val id: UUID,
        val author: Author,
        val createdAt: ZonedDateTime
    ) : ThreadResponse()

    data class LikeRemoved(
        val id: UUID,
        val author: Author,
        val createdAt: ZonedDateTime
    ) : ThreadResponse()

    data class NewDislike(
        val id: UUID,
        val author: Author,
        val createdAt: ZonedDateTime
    ) : ThreadResponse()

    data class DislikeRemoved(
        val id: UUID,
        val author: Author,
        val createdAt: ZonedDateTime
    ) : ThreadResponse()
}