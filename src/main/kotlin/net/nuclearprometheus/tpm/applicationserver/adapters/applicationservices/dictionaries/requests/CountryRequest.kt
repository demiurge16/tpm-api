package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country

sealed class CountryRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Country>(
        page,
        size,
        sort,
        search,
        mapOf(
            "code" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
            "name" to Comparator { o1, o2 -> o1.name.compareTo(o2.name, ignoreCase = true) }
        )
    ) {

        override fun toString(): String {
            return "CountryRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }
}
