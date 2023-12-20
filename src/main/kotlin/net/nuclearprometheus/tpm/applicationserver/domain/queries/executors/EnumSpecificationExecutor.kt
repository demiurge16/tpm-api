package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.EnumSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class EnumSpecificationExecutor<TEntity : Any, TValue : Enum<TValue>>(valueGetter: ValueGetter<TEntity, TValue>) : SpecificationExecutor<TEntity, TValue>(valueGetter) {
    override fun execute(entity: TEntity, specification: Specification.ParameterizedSpecification<TEntity>): Boolean {
        return when (specification) {
            is EnumSpecification.Eq<*, *> -> {
                valueGetter(entity) == specification.value
            }
            is EnumSpecification.AnyElement<*, *> -> {
                val value = valueGetter(entity) ?: return false
                value in specification.value
            }
            is EnumSpecification.NoneElement<*, *> -> {
                val value = valueGetter(entity) ?: return false
                value !in specification.value
            }
            is EnumSpecification.IsNull -> {
                valueGetter(entity) == null
            }
            else -> {
                throw IllegalArgumentException("Invalid filter expression: ${specification.name}")
            }
        }
    }
}