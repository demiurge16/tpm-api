package net.nuclearprometheus.tpm.applicationserver.domain.queries

fun <TEntity : Any> query(block: QueryBuilder<TEntity>.() -> Unit): Query<TEntity> {
    val builder = QueryBuilder<TEntity>()
    builder.block()
    return builder.build()
}
