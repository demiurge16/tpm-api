package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency

class CurrencyListRequest(
    page: Int?,
    size: Int?,
    sort: String?,
    direction: String?,
    search: String?
) : FilteredRequest<Currency>(page, size, sort, direction, search) {

    override fun sortComparator(): Comparator<Currency> {
        return when (sort) {
            "code.value" -> Comparator<Currency> { o1, o2 -> o1.id.value.compareTo(o2.id.value) }
            "name" -> Comparator<Currency> { o1, o2 -> o1.name.compareTo(o2.name) }
            else -> Comparator<Currency> { _, _ -> 0 }
        }.let {
            if (direction == "DESC") it.reversed() else it
        }
    }

    override fun toString(): String {
        return "CurrencyListQuery(page=$page, size=$size, sort=$sort, direction=$direction, search=$search)"
    }
}