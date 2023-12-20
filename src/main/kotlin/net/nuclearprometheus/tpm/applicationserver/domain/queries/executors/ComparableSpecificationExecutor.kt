package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.ComparableSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class ComparableSpecificationExecutor<TEntity : Any, TValue : Comparable<TValue>>(valueGetter: ValueGetter<TEntity, TValue>) : SpecificationExecutor<TEntity, TValue>(valueGetter) {
    override fun execute(entity: TEntity, specification: Specification.ParameterizedSpecification<TEntity>): Boolean {
        return when (specification) {
            is ComparableSpecification.Eq<*, *> -> {
                valueGetter(entity) == specification.value
            }
            is ComparableSpecification.Gt<*, *> -> executeIfType<ComparableSpecification.Gt<TEntity, TValue>>(specification) {
                val value = valueGetter(entity) ?: return@executeIfType false
                value > it.value
            }
            is ComparableSpecification.Gte<*, *> -> executeIfType<ComparableSpecification.Gte<TEntity, TValue>>(specification) {
                val value = valueGetter(entity) ?: return@executeIfType false
                value >= it.value
            }
            is ComparableSpecification.Lt<*, *> -> executeIfType<ComparableSpecification.Lt<TEntity, TValue>>(specification) {
                val value = valueGetter(entity) ?: return@executeIfType false
                value < it.value
            }
            is ComparableSpecification.Lte<*, *> -> executeIfType<ComparableSpecification.Lte<TEntity, TValue>>(specification) {
                val value = valueGetter(entity) ?: return@executeIfType false
                value <= it.value
            }
            is ComparableSpecification.AnyElement<*, *> -> {
                val value = valueGetter(entity) ?: return false
                specification.value.any { value == it }
            }
            is ComparableSpecification.NoneElement<*, *> -> {
                val value = valueGetter(entity) ?: return false
                specification.value.none { value == it }
            }
            is ComparableSpecification.IsNull -> {
                valueGetter(entity) == null
            }
            else -> {
                throw IllegalArgumentException("Invalid filter expression: ${specification.name}")
            }
        }
    }
}