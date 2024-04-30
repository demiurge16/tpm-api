package net.nuclearprometheus.tpm.applicationserver.domain.model.thread.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Tag
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.*

object TagSpecificationBuilder : SpecificationBuilder<Tag>() {
    val id = uniqueValue("id", UUID::class)
    val name = string("name")
    val threadId = uniqueValue("threadId", UUID::class)
}