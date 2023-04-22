package net.nuclearprometheus.tpm.applicationserver.adapters.common.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.PageableImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.queries.Query
import net.nuclearprometheus.tpm.applicationserver.queries.createQuery
import net.nuclearprometheus.tpm.applicationserver.queries.emptyQuery

// TODO: make this an interface
abstract class FilteredRequest<TEntity : Any>(
    val page: Int?,
    val size: Int?,
    val sort: String?,
    val search: String?,
    protected val sorters: Map<String, Comparator<TEntity>>
) {
    private val logger = loggerFor(this::class.java)

    val paged: Boolean
        get() = page != null && size != null

    fun offset() = if (paged) (page!!) * size!! else 0
    fun limit() = if (paged) size!! else Int.MAX_VALUE
    fun searchQuery(): Query<TEntity> = search?.let { createQuery(it) } ?: emptyQuery()

    fun sortComparator(): Comparator<TEntity> {
        if (sort.isNullOrEmpty()) {
            return Comparator { _, _ -> 0 }
        }

        val expressionTokens = sort.split("&")

        logger.debug("Sort expression tokens: $expressionTokens")

        return expressionTokens.map { token ->
            val (sort, direction) = token.split(":")
            val comparator = sorters[sort] ?: throw IllegalArgumentException("Invalid sort expression: $token")

            if (direction == "desc") comparator.reversed() else comparator
        }.reduce { acc, comparator -> acc.thenComparing(comparator) }
    }
}

fun <TEntity : Any> List<TEntity>.applyQuery(query: FilteredRequest<TEntity>): Pageable<TEntity> {
    val items = this.asSequence()
        .filter { query.searchQuery().evaluate(it) }
        .sortedWith(query.sortComparator())
        .drop(query.offset())
        .take(query.limit())
        .toList()
    val totalElements = this.size
    val totalPages = if (query.paged) (totalElements / query.size!!) + 1 else 1

    return PageableImpl(items = items, totalPages = totalPages, totalElements = totalElements)
}
