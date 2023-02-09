package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client

class ClientListRequest(
    page: Int?,
    size: Int?,
    sort: String?,
    direction: String?,
    search: String?
) : FilteredRequest<Client>(page, size, sort, direction, search) {

    override fun sortComparator(): Comparator<Client> {
        return when (sort) {
            "id.value" -> Comparator<Client> { o1, o2 -> compareValues(o1.id.value, o2.id.value) }
            "name" -> Comparator<Client> { o1, o2 -> compareValues(o1.name, o2.name) }
            else -> Comparator<Client> { _, _ -> 0 }
        }.let {
            if (direction == "DESC") it.reversed() else it
        }
    }

    override fun toString(): String {
        return "ClientListQuery(page=$page, size=$size, sort=$sort, direction=$direction, search=$search)"
    }
}