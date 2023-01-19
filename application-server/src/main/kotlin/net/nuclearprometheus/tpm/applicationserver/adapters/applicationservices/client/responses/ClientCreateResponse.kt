package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import java.util.*

data class ClientCreateResponse(
    val id: UUID,
    var name: String,
    var email: String,
    var phone: String,
    var address: String,
    var city: String,
    var state: String,
    var zip: String,
    var countryCode: String,
    var vat: String,
    var notes: String,
    var clientTypeId: UUID,
    var active: Boolean,
)
