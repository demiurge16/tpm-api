package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests

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

    fun sortComparator(): Comparator<Country> {
        return when (sort) {
            "code.value" -> Comparator<Country> { o1, o2 -> o1.code.value.compareTo(o2.code.value) }
            "name" -> Comparator<Country> { o1, o2 -> o1.name.compareTo(o2.name) }
            else -> Comparator<Country> { _, _ -> 0 }
        }.let {
            if (direction == "DESC") it.reversed() else it
        }
    }

    override fun toString(): String {
        return "CountryListQuery(page=$page, size=$size, sort=$sort, direction=$direction, search=$search)"
    }
}
