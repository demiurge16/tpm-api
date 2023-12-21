package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

class SpecificationExecutors<TEntity : Any>(private val executors: Map<String, SpecificationExecutor<TEntity, *>>) {

    operator fun get(name: String): SpecificationExecutor<TEntity, *> = executors[name]
        ?: throw IllegalArgumentException("No specification executor for name '$name'")
}

