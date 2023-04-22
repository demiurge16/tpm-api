package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language

sealed class LanguageRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Language>(
        page,
        size,
        sort,
        search,
        mapOf(
            "code" to Comparator { o1, o2 -> compareValues(o1.id.value, o2.id.value) },
            "name" to Comparator { o1, o2 -> compareValues(o1.name, o2.name) },
            "iso6392t" to Comparator { o1, o2 -> compareValues(o1.iso6392T, o2.iso6392T) },
            "iso6392b" to Comparator { o1, o2 -> compareValues(o1.iso6392B, o2.iso6392B) },
            "iso6391" to Comparator { o1, o2 -> compareValues(o1.iso6391, o2.iso6391) }
        )
    ) {

        override fun toString(): String {
            return "LanguageRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }
}
