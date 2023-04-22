package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import io.swagger.v3.oas.annotations.media.Schema
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.PageableImpl
import java.util.*

sealed class ClientResponse {

    @Schema(name = "ClientResponse.Page")
    data class Page(
        override val items: List<Client>,
        override val totalPages: Int,
        override val totalElements: Int,
    ) : ClientResponse(), Pageable<Client> by PageableImpl(items, totalPages, totalElements)

    @Schema(name = "ClientResponse.Client")
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

    @Schema(name = "ClientResponse.Status")
    data class Status(
        val id: UUID,
        val active: Boolean
    )
}
