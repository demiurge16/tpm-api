package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.StringSpecification

class StringSpecificationExecutor<TEntity : Any>(valueGetter: ValueGetter<TEntity, String>) : SpecificationExecutor<TEntity, String>(valueGetter) {
    override fun execute(entity: TEntity, specification: Specification.ParameterizedSpecification<TEntity>): Boolean {
        return when (specification) {
            is StringSpecification.Eq<*> -> {
                valueGetter(entity) == specification.value
            }
            is StringSpecification.Contains<*> -> {
                val value = valueGetter(entity) ?: return false
                value.contains(specification.value)
            }
            is StringSpecification.AnyElement<*> -> {
                valueGetter(entity) in specification.value
            }
            is StringSpecification.NoneElement<*> -> {
                valueGetter(entity) !in specification.value
            }
            is StringSpecification.IsNull -> {
                valueGetter(entity) == null
            }
            is StringSpecification.IsEmpty -> {
                valueGetter(entity).isNullOrEmpty()
            }
            else -> {
                throw IllegalArgumentException("Invalid filter expression: ${specification.name}")
            }
        }
    }
}
