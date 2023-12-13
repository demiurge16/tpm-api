package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Direction
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

fun <TEntity : Any> Query<TEntity>.toPageable(): Pageable =
    PageRequest.of(
        page ?: 0,
        size ?: Int.MAX_VALUE,
        Sort.by(
            sort.order.map {
                when (it.direction) {
                    Direction.ASC -> Sort.Order.asc(it.name)
                    Direction.DESC -> Sort.Order.desc(it.name)
                }
            }
        )
    )
