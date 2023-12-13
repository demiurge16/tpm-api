package net.nuclearprometheus.tpm.applicationserver.domain.model.thread.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadStatus
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.time.ZonedDateTime
import java.util.*

object ThreadSpecificationBuilder : SpecificationBuilder<Thread>() {
    val id = uniqueValue("id", UUID::class)
    val title = string("title")
    val createdAt = comparable("createdAt", ZonedDateTime::class)
    val status = enum("status", ThreadStatus::class)
    val authorId = uniqueValue("authorId", UUID::class)
    val projectId = uniqueValue("projectId", UUID::class)
    val tags = collection("tags", String::class)
}

