package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client

class ListClients(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<Client>(page, size, sort, search) {

    override fun toString(): String {
        return "ClientRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}
