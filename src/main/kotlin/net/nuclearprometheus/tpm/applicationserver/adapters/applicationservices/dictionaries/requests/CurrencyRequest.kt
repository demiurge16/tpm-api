package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency

sealed class CurrencyRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Currency>(page, size, sort, search) {

        override fun toString(): String {
            return "CurrencyRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }
}
