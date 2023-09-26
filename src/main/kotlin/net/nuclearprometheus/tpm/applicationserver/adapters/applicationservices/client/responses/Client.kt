package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Country
import java.util.*

data class Client(
    val id: UUID,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val state: String,
    val zip: String,
    val country: Country,
    val vat: String,
    val notes: String,
    val type: ClientType,
    val active: Boolean,
)

