package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType

sealed class ClientTypeRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        direction: String?,
        search: String?
    ) : FilteredRequest<ClientType>(page, size, sort, direction, search) {

        override fun sortComparator(): Comparator<ClientType> {
            return when (sort) {
                "id.value" -> Comparator<ClientType> { o1, o2 -> compareValues(o1.id.value, o2.id.value) }
                "name" -> Comparator<ClientType> { o1, o2 -> compareValues(o1.name, o2.name) }
                else -> Comparator<ClientType> { _, _ -> 0 }
            }.let {
                if (direction == "DESC") it.reversed() else it
            }
        }

        override fun toString(): String {
            return "ClientTypeRequest.List(page=$page, size=$size, sort=$sort, direction=$direction, search=$search)"
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