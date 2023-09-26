package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.createSearch
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.nonFiltered
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.SortDirection
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor

abstract class FilteredRequest<TEntity : Any>(
    val page: Int?,
    val size: Int?,
    val sort: String?,
    val search: String?
) {
    private val logger = loggerFor(this::class.java)

    fun sort(): List<Sort> {
        if (sort.isNullOrEmpty()) {
            return emptyList()
        }

        val expressionTokens = sort.split("&")
        return expressionTokens.map { token ->
            val (sort, direction) = token.split(":")
            Sort(sort, if (direction == "desc") SortDirection.DESC else SortDirection.ASC)
        }
    }

    fun toQuery() = Query<TEntity>(
        page = page,
        size = size,
        sort = sort(),
        search = search?.let { createSearch(it) } ?: nonFiltered()
    )
}
