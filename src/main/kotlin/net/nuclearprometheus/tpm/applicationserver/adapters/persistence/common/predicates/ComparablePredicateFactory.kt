package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.predicates

import jakarta.persistence.criteria.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.ComparableSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class ComparablePredicateFactory<TEntity : Any, TDatabaseModel : Any, TValue : Comparable<TValue>>(
    private val expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<TValue>
) : PredicateFactory<TEntity, TDatabaseModel>() {

    @Suppress("UNCHECKED_CAST")
    override fun createPredicate(
        root: Root<TDatabaseModel>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder,
        specification: Specification.ParameterizedSpecification<TEntity>
    ): Predicate {
        val expression = expressionSupplier(root, query, criteriaBuilder)
        return when (specification) {
            is ComparableSpecification.Eq<*, *> -> criteriaBuilder.equal(expression, specification.value as Expression<TValue>)
            is ComparableSpecification.Gt<*, *> -> criteriaBuilder.greaterThan(expression, specification.value as Expression<TValue>)
            is ComparableSpecification.Gte<*, *> -> criteriaBuilder.greaterThanOrEqualTo(expression, specification.value as Expression<TValue>)
            is ComparableSpecification.Lt<*, *> -> criteriaBuilder.lessThan(expression, specification.value as Expression<TValue>)
            is ComparableSpecification.Lte<*, *> -> criteriaBuilder.lessThanOrEqualTo(expression, specification.value as Expression<TValue>)
            is ComparableSpecification.AnyElement<*, *> -> expression.`in`(specification.value)
            is ComparableSpecification.NoneElement<*, *> -> criteriaBuilder.not(expression.`in`(specification.value))
            is ComparableSpecification.IsNull -> criteriaBuilder.isNull(expression)
            else -> throw IllegalArgumentException("Invalid specification type")
        }
    }
}