package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.SortDirection
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

fun <TEntity : Any> Query<TEntity>.toPageable(): Pageable =
    PageRequest.of(
        page ?: 0,
        size ?: 10,
        Sort.by(
            sort.map {
                when (it.direction) {
                    SortDirection.ASC -> Sort.Order.asc(it.field)
                    SortDirection.DESC -> Sort.Order.desc(it.field)
                }
            }
        )
    )
