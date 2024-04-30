package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.predicates

import jakarta.persistence.criteria.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.UniqueValueSpecification

class UniqueValuePredicateFactory<TEntity : Any, TDatabaseModel : Any, TValue : Any>(
    private val expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<TValue>
) : PredicateFactory<TEntity, TDatabaseModel>() {

    override fun createPredicate(
        root: Root<TDatabaseModel>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder,
        specification: Specification.ParameterizedSpecification<TEntity>
    ): Predicate {
        val expression = expressionSupplier(root, query, criteriaBuilder)
        return when (specification) {
            is UniqueValueSpecification.Eq<*, *> -> criteriaBuilder.equal(expression, specification.value)
            is UniqueValueSpecification.AnyElement<*, *> -> expression.`in`(specification.value)
            is UniqueValueSpecification.NoneElement<*, *> -> criteriaBuilder.not(expression.`in`(specification.value))
            is UniqueValueSpecification.IsNull -> criteriaBuilder.isNull(expression)
            else -> throw IllegalArgumentException("Invalid specification type")
        }
    }
}