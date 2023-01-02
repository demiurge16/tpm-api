package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CountryView
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Country
import net.nuclearprometheus.translationprojectmanager.queries.Query
import net.nuclearprometheus.translationprojectmanager.queries.emptyQuery
import net.nuclearprometheus.translationprojectmanager.queries.parseQuery

class CountryListQuery(
    val page: Int?,
    val size: Int?,
    val sort: String?,
    val direction: String?,
    val search: String?
) {
    val paged: Boolean
        get() = page != null && size != null
    val sorted: Boolean
        get() = sort != null && direction != null

    fun offset() = if (paged) (page!!) * size!! else 0
    fun limit() = if (paged) size!! else Int.MAX_VALUE
    fun searchQuery(): Query<Country> = search?.let { parseQuery(it) } ?: emptyQuery()
}
