package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language

class LanguageListRequest(
    page: Int?,
    size: Int?,
    sort: String?,
    direction: String?,
    search: String?
) : FilteredRequest<Language>(page, size, sort, direction, search) {

    override fun sortComparator(): Comparator<Language> {
        return when (sort) {
            "code.value" -> Comparator<Language> { o1, o2 -> o1.code.value.compareTo(o2.code.value) }
            "name" -> Comparator<Language> { o1, o2 -> o1.name.compareTo(o2.name) }
            else -> Comparator<Language> { _, _ -> 0 }
        }.let {
            if (direction == "DESC") it.reversed() else it
        }
    }

    override fun toString(): String {
        return "LanguageListQuery(page=$page, size=$size, sort=$sort, direction=$direction, search=$search)"
    }
}