package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.time.ZonedDateTime
import java.util.*

data class Reply(
    val id: UUID,
    val content: String,
    val author: User,
    val createdAt: ZonedDateTime,
    val deleted: Boolean,
    val parentReplyId: UUID?,
    val threadId: UUID,
    val likes: List<Like>,
    val dislikes: List<Dislike>
)
