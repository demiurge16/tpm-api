package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses

import io.swagger.v3.oas.annotations.media.Schema
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.PageableImpl
import java.util.*

sealed class ClientTypeResponse {

    @Schema(name = "ClientTypeResponse.Page")
    data class Page(
        override val items: List<View>,
        override val totalPages: Int,
        override val totalElements: Int,
    ) : ClientTypeResponse(), Pageable<View> by PageableImpl(items, totalPages, totalElements)

    @Schema(name = "ClientTypeResponse.View")
    data class View(
        val id: UUID,
        val name: String,
        val description: String,
        val corporate: Boolean,
        val active: Boolean
    )

    @Schema(name = "ClientTypeResponse.ActivityStatus")
    data class ActivityStatus(
        val id: UUID,
        val active: Boolean
    )
}
