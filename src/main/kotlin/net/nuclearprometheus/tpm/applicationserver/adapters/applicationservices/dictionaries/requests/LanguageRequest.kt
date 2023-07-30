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
            "name" to Comparator { o1, o2 -> o1.name.compareTo(o2.name, ignoreCase = true) },
            "iso6392t" to Comparator { o1, o2 ->
                compareValues(
                    o1.iso6392T.orEmpty().lowercase(),
                    o2.iso6392T.orEmpty().lowercase()
                )
            },
            "iso6392b" to Comparator { o1, o2 ->
                compareValues(
                    o1.iso6392B.orEmpty().lowercase(),
                    o2.iso6392B.orEmpty().lowercase()
                )
            },
            "iso6391" to Comparator { o1, o2 ->
                compareValues(
                    o1.iso6391.orEmpty().lowercase(),
                    o2.iso6391.orEmpty().lowercase()
                )
            }
        )
    ) {

        override fun toString(): String {
            return "LanguageRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }
}
