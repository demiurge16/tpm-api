package net.nuclearprometheus.tpm.applicationserver.domain.model.client.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder

object ClientTypeSpecificationBuilder : SpecificationBuilder<ClientType>() {
    val id = uniqueValue("id", String::class)
    val name = string("name")
    val corporate = boolean("corporate")
    val active = boolean("active")
}
