package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType

sealed class ClientTypeRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<ClientType>(
        page,
        size,
        sort,
        search,
        mapOf(
            "id" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
            "name" to Comparator { o1, o2 -> compareValues(o1.name, o2.name) }
        )
    ) {

        override fun toString(): String {
            return "ClientTypeRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }

    data class Create(
        val name: String,
        val description: String,
        val corporate: Boolean
    ) : ClientTypeRequest()

    data class Update(
        val name: String,
        val description: String,
        val corporate: Boolean
    ) : ClientTypeRequest()
}