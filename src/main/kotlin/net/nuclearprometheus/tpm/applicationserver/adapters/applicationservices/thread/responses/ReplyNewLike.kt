package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.time.ZonedDateTime
import java.util.*

data class ReplyNewLike(
    val id: UUID,
    val author: User,
    val createdAt: ZonedDateTime
)