package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.*

object UnitSpecificationBuilder : SpecificationBuilder<Unit>() {
    val id = uniqueValue("id", UUID::class)
    val name = string("name")
    val volume = comparable("volume", Int::class)
    val measurement = enum("measurement", Measurement::class)
    val active = boolean("active")
}