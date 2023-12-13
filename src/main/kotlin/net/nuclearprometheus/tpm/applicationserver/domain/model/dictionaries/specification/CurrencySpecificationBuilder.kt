package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder

object CurrencySpecificationBuilder : SpecificationBuilder<Currency>() {
    val code = uniqueValue("code", String::class)
    val name = string("name")
}