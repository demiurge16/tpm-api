package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.time.ZonedDateTime
import java.util.*

data class Like(
    val id: UUID,
    val createdAt: ZonedDateTime,
    val author: User
)