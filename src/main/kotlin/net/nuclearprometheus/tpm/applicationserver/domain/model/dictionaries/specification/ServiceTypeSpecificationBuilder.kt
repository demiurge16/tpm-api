package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.*

object ServiceTypeSpecificationBuilder : SpecificationBuilder<ServiceType>() {
    val id = uniqueValue("id", UUID::class)
    val name = string("name")
    val active = boolean("active")
}