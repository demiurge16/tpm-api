package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country

sealed class CountryRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Country>( page, size, sort, search) {

        override fun toString(): String {
            return "CountryRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }
}

