package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.SortDirection
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sorter

fun <TEntity : Any> unsorted() = Sort<TEntity>(emptyList())

fun <TEntity : Any> orderBy(init: SortBuilder<TEntity>.() -> Unit): Sort<TEntity> {
    val builder = SortBuilder<TEntity>()
    builder.init()
    return builder.build()
}

class SortBuilder<TEntity : Any>  {
    private val sorts = mutableListOf<Sorter<TEntity>>()

    fun ascending(field: String): SortBuilder<TEntity> {
        sorts.add(Sorter(field, SortDirection.ASC))
        return this
    }

    fun descending(field: String): SortBuilder<TEntity> {
        sorts.add(Sorter(field, SortDirection.DESC))
        return this
    }

    fun build(): Sort {
        return Sort(sorts)
    }
}
