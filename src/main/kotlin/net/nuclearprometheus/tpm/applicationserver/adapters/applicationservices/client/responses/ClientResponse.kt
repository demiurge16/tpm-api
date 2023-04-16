package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import java.util.*

sealed class ClientResponse {

    data class View(
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
    ) {

        data class CountryView(
            val code: String,
            val name: String,
        )

        data class ClientTypeView(
            val id: UUID,
            val name: String,
            val description: String,
            val corporate: Boolean,
        )
    }

    data class ActivityStatus(
        val id: UUID,
        val active: Boolean
    )
}
