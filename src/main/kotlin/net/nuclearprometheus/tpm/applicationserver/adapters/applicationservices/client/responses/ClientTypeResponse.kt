package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

sealed class ClientTypeResponse {

    @Schema(name = "ClientTypeResponse.Page")
    data class Page(
        val items: List<ClientType>,
        val totalPages: Int,
        val totalElements: Int,
    ) : ClientTypeResponse()

    @Schema(name = "ClientTypeResponse.ClientType")
    data class ClientType(
        val id: UUID,
        val name: String,
        val description: String,
        val corporate: Boolean,
        val active: Boolean
    )

    @Schema(name = "ClientTypeResponse.Status")
    data class Status(
        val id: UUID,
        val active: Boolean
    )
}
