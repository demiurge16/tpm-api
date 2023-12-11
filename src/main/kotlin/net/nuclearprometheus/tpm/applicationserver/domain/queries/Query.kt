package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class Query<TEntity : Any>(
    val search: Specification<TEntity>? = null,
    val orderBy: Sort<TEntity>? = null,
    val page: Pair<Int, Int>? = null,
    val offset: Int? = null,
    val limit: Int? = null
)
