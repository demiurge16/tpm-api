package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language

class ListLanguages(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<Language>(page, size, sort, search) {
    override fun toString(): String {
        return "LanguageRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}
