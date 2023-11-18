package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl.nonFiltered
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.unsorted

data class Query<TEntity : Any>(
    val page: Int? = null,
    val size: Int? = null,
    val sort: Sort<TEntity> = unsorted(),
    val search: Search<TEntity> = nonFiltered()
)
