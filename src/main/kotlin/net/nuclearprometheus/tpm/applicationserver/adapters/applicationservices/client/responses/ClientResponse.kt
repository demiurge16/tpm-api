package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import io.swagger.v3.oas.annotations.media.Schema
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.PageableImpl
import java.util.*

sealed class ClientResponse {

    @Schema(name = "ClientResponse.Page")
    data class Page(
        override val items: List<View>,
        override val totalPages: Int,
        override val totalElements: Int,
    ) : ClientResponse(), Pageable<View> by PageableImpl(items, totalPages, totalElements)

    @Schema(name = "ClientResponse.View")
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

        @Schema(name = "ClientResponse.View.CountryView")
        data class CountryView(
            val code: String,
            val name: String,
        )

        @Schema(name = "ClientResponse.View.ClientTypeView")
        data class ClientTypeView(
            val id: UUID,
            val name: String,
            val description: String,
            val corporate: Boolean,
        )
    }

    @Schema(name = "ClientResponse.ActivityStatus")
    data class ActivityStatus(
        val id: UUID,
        val active: Boolean
    )
}
