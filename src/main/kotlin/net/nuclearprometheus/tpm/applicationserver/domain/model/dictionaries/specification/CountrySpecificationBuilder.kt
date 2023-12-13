package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder

object CountrySpecificationBuilder : SpecificationBuilder<Country>() {
    val code = uniqueValue("code", String::class)
    val name = string("name")
}