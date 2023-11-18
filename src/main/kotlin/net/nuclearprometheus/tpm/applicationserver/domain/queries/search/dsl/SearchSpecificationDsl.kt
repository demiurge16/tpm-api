package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations.*

fun <TEntity : Any> searchSpecification(init: SearchSpecificationBuilder<TEntity>.() -> Unit): SearchSpecification<TEntity> {
    val builder = SearchSpecificationBuilder<TEntity>()
    builder.init()
    return builder.build()
}

fun <TEntity : Any> nonFiltered(): Search<TEntity> {
    return Search(root = True())
}
