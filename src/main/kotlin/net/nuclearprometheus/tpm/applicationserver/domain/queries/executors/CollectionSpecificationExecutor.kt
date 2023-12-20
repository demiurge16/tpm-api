package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.CollectionSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class CollectionSpecificationExecutor<TEntity : Any, TValue : Any>(valueGetter: ValueGetter<TEntity, Collection<TValue>>) : SpecificationExecutor<TEntity, Collection<TValue>>(valueGetter) {
    override fun execute(entity: TEntity, specification: Specification.ParameterizedSpecification<TEntity>): Boolean {
        return when (specification) {
            is CollectionSpecification.ContainsElement<*, *> -> {
                val value = valueGetter(entity) ?: return false
                value.contains(specification.value)
            }
            is CollectionSpecification.AllElement<*, *> -> {
                val value = valueGetter(entity) ?: return false
                specification.value.all { value.contains(it) }
            }
            is CollectionSpecification.AnyElement<*, *> -> {
                val value = valueGetter(entity) ?: return false
                specification.value.any { value.contains(it) }
            }
            is CollectionSpecification.NoneElement<*, *> -> {
                val value = valueGetter(entity) ?: return false
                specification.value.none { value.contains(it) }
            }
            is CollectionSpecification.IsNull -> {
                valueGetter(entity) == null
            }
            is CollectionSpecification.IsEmpty -> {
                valueGetter(entity).isNullOrEmpty()
            }
            else -> {
                throw IllegalArgumentException("Invalid filter expression: ${specification.name}")
            }
        }
    }
}