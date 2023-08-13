package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import java.time.ZonedDateTime
import java.util.*

data class Like(
    val id: UUID,
    val createdAt: ZonedDateTime,
    val author: Author
)