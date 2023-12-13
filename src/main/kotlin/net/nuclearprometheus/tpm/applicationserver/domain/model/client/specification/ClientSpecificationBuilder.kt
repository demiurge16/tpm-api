package net.nuclearprometheus.tpm.applicationserver.domain.model.client.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder

object ClientSpecificationBuilder : SpecificationBuilder<Client>() {
    val id = uniqueValue("id", String::class)
    val name = string("name")
    val email = string("email")
    val phone = string("phone")
    val address = string("address")
    val city = string("city")
    val state = string("state")
    val zip = string("zip")
    val countryCode = uniqueValue("countryCode", String::class)
    val clientTypeId = uniqueValue("clientTypeId", String::class)
    val vat = string("vat")
    val active = boolean("active")
}

