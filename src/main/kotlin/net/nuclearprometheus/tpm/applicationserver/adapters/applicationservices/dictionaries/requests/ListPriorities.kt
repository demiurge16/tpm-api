package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority

class ListPriorities(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<Priority>( page, size, sort, search) {

    override fun toString(): String {
        return "PriorityRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}
