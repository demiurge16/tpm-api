package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class Query<TEntity : Any>(
    val specification: Specification<TEntity>,
    val sort: Sort<TEntity>,
    val page: Int? = null,
    val size: Int? = null
)
