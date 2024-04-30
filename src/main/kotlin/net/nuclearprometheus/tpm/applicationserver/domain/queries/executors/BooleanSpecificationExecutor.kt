package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.BooleanSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class BooleanSpecificationExecutor<TEntity : Any>(valueGetter: ValueGetter<TEntity, Boolean>) : SpecificationExecutor<TEntity, Boolean>(valueGetter) {
    override fun execute(entity: TEntity, specification: Specification.ParameterizedSpecification<TEntity>): Boolean {
        return when (specification) {
            is BooleanSpecification.Eq -> {
                valueGetter(entity) == specification.value
            }
            is BooleanSpecification.IsNull -> {
                valueGetter(entity) == null
            }
            else -> {
                throw IllegalArgumentException("Invalid filter expression: ${specification.name}")
            }
        }
    }
}
