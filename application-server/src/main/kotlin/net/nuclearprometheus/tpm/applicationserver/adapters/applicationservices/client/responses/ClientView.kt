package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CountryView
import java.util.UUID

data class ClientView(
    val id: UUID,
    var name: String,
    var email: String,
    var phone: String,
    var address: String,
    var city: String,
    var state: String,
    var zip: String,
    var country: CountryView,
    var vat: String,
    var notes: String,
    var type: ClientTypeView,
    var active: Boolean,
)
