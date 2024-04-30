package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort

fun <TEntity : Any> unsorted(): Sort<TEntity> {
    return Sort(emptyList())
}