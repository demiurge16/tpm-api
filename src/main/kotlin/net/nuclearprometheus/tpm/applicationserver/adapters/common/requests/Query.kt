package net.nuclearprometheus.tpm.applicationserver.adapters.common.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Page
import net.nuclearprometheus.tpm.applicationserver.queries.Query
import net.nuclearprometheus.tpm.applicationserver.queries.createQuery
import net.nuclearprometheus.tpm.applicationserver.queries.emptyQuery

// TODO: make this an interface
abstract class FilteredRequest<TEntity : Any>(
    val page: Int?,
    val size: Int?,
    val sort: String?,
    val direction: String?,
    val search: String?
) {
    val paged: Boolean
        get() = page != null && size != null

    fun offset() = if (paged) (page!!) * size!! else 0
    fun limit() = if (paged) size!! else Int.MAX_VALUE
    fun searchQuery(): Query<TEntity> = search?.let { createQuery(it) } ?: emptyQuery()
    abstract fun sortComparator(): Comparator<TEntity>
}

fun <TEntity : Any> List<TEntity>.applyQuery(query: FilteredRequest<TEntity>): Page<TEntity> {
    val items = this.asSequence()
        .filter { query.searchQuery().evaluate(it) }
        .sortedWith(query.sortComparator())
        .drop(query.offset())
        .take(query.limit())
        .toList()
    val totalElements = this.size
    val totalPages = if (query.paged) (totalElements / query.size!!) + 1 else 1

    return Page(items = items, totalPages = totalPages, totalElements = totalElements)
}
