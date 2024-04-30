package net.nuclearprometheus.tpm.applicationserver.domain.model.thread.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.time.ZonedDateTime
import java.util.*

object ReplySpecificationBuilder : SpecificationBuilder<Reply>() {
    val id = uniqueValue("id", UUID::class)
    val createdAt = comparable("createdAt", ZonedDateTime::class)
    val deleted = boolean("deleted")
    val authorId = uniqueValue("authorId", UUID::class)
    val parentReplyId = uniqueValue("parentReplyId", UUID::class)
    val threadId = uniqueValue("threadId", UUID::class)
}