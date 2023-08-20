package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class ServiceTypeResponse {
    data class ServiceType(
        val id: UUID,
        val name: String,
        val description: String,
        val active: Boolean
    ) : ServiceTypeResponse()

    data class Status(
        val id: UUID,
        val active: Boolean
    ) : ServiceTypeResponse()
}