package net.nuclearprometheus.tpm.applicationserver.domain.model.thread.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadLike
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.time.ZonedDateTime
import java.util.*

object ReplyLikeSpecificationBuilder : SpecificationBuilder<ThreadLike>() {
    val id = uniqueValue("id", UUID::class)
    val createdAt = comparable("createdAt", ZonedDateTime::class)
    val replyId = uniqueValue("replyId", UUID::class)
    val userId = uniqueValue("userId", UUID::class)
}