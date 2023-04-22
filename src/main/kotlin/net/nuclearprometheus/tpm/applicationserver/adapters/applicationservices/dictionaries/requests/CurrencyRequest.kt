package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language

sealed class CurrencyRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Currency>(
        page,
        size,
        sort,
        search,
        mapOf(
            "code" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
            "name" to Comparator { o1, o2 -> compareValues(o1.name, o2.name) }
        )
    ) {

        override fun toString(): String {
            return "CurrencyRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }
}
