package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

abstract class SpecificationExecutor<TEntity : Any, TValue : Any>(protected val valueGetter: ValueGetter<TEntity, TValue>) {
    abstract fun execute(entity: TEntity, specification: Specification.ParameterizedSpecification<TEntity>): Boolean

    protected inline fun <reified TSpec : Specification.ParameterizedSpecification<TEntity>> executeIfType(
        specification: Specification.ParameterizedSpecification<TEntity>,
        execution: (TSpec) -> Boolean
    ): Boolean {
        if (specification is TSpec) {
            return execution(specification)
        }
        return false
    }
}