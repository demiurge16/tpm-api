package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType

class ListClientTypes(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<ClientType>(page, size, sort, search) {

    override fun toString(): String {
        return "ClientTypeRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}
