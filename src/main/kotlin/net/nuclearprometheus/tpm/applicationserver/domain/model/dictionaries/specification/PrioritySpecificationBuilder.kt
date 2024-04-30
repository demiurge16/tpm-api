package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.*

object PrioritySpecificationBuilder : SpecificationBuilder<Priority>() {
    val id = uniqueValue("id", UUID::class)
    val name = string("name")
    val value = comparable("value", Int::class)
    val active = boolean("active")
}