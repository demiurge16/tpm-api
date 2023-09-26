package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry

class ListIndustries(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<Industry>(page, size, sort, search) {

    override fun toString(): String {
        return "IndustryRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}