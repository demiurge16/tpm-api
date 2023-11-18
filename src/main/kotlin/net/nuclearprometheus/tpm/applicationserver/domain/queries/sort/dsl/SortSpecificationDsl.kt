package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl

fun <TEntity : Any> sortSpecification(init: SortSpecificationBuilder<TEntity>.() -> Unit): SortSpecification<TEntity> {
    val builder = SortSpecificationBuilder<TEntity>()
    builder.init()
    return builder.build()
}
