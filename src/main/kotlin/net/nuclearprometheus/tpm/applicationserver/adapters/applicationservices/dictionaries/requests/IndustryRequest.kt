package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry

sealed class IndustryRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Industry>(page, size, sort, search) {

        override fun toString(): String {
            return "IndustryRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }

    data class Create(
        val name: String,
        val description: String,
    ) : IndustryRequest()

    data class Update(
        val name: String,
        val description: String,
    ) : IndustryRequest()
}