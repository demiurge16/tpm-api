package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.UniqueValueSpecification

class UniqueValueSpecificationExecutor<TEntity : Any, TValue : Any>(valueGetter: ValueGetter<TEntity, TValue>) : SpecificationExecutor<TEntity, TValue>(valueGetter) {

    override fun execute(entity: TEntity, specification: Specification.ParameterizedSpecification<TEntity>): Boolean {
        return when (specification) {
            is UniqueValueSpecification.Eq<*, *> -> executeIfType<UniqueValueSpecification.Eq<TEntity, TValue>>(specification) {
                valueGetter(entity) == it.value
            }
            is UniqueValueSpecification.AnyElement<*, *> -> executeIfType<UniqueValueSpecification.AnyElement<TEntity, TValue>>(specification) {
                valueGetter(entity) in it.value
            }
            is UniqueValueSpecification.NoneElement<*, *> -> executeIfType<UniqueValueSpecification.NoneElement<TEntity, TValue>>(specification) {
                valueGetter(entity) !in it.value
            }
            is UniqueValueSpecification.IsNull -> {
                valueGetter(entity) == null
            }
            else -> {
                throw IllegalArgumentException("Invalid filter expression: ${specification.name}")
            }
        }
    }
}