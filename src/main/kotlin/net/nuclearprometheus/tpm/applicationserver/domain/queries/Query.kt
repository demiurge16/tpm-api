package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.nonFiltered
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.unsorted

data class Query<TEntity : Any>(
    val page: Int? = null,
    val size: Int? = null,
    val sort: List<Sort> = unsorted(),
    val search: Search<TEntity> = nonFiltered()
)
