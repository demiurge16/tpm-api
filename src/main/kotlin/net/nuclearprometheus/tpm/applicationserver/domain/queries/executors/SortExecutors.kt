package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

class SortExecutors<TEntity : Any>(private val executors: Map<String, Comparator<TEntity>>) {
    operator fun get(name: String): Comparator<TEntity> = executors[name] ?: throw IllegalArgumentException("Invalid sort expression: $name")
}