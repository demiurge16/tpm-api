package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.unsorted
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.nonFiltered
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class Query<TEntity : Any>(
    val specification: Specification<TEntity> = nonFiltered(),
    val sort: Sort<TEntity> = unsorted(),
    val page: Int? = null,
    val size: Int? = null
)
