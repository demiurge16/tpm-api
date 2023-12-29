package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.predicates

import jakarta.persistence.criteria.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.StringSpecification

class StringPredicateFactory<TEntity : Any, TDatabaseModel : Any>(
    private val expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<String>
) : PredicateFactory<TEntity, TDatabaseModel>() {

    override fun createPredicate(
        root: Root<TDatabaseModel>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder,
        specification: Specification.ParameterizedSpecification<TEntity>
    ): Predicate {
        val expression = expressionSupplier(root, query, criteriaBuilder)
        return when (specification) {
            is StringSpecification.Eq -> criteriaBuilder.equal(expression, specification.value)
            is StringSpecification.Contains -> criteriaBuilder.like(expression, "%${specification.value}%")
            is StringSpecification.AnyElement -> expression.`in`(specification.value)
            is StringSpecification.NoneElement -> criteriaBuilder.not(expression.`in`(specification.value))
            is StringSpecification.IsNull -> criteriaBuilder.isNull(expression)
            is StringSpecification.IsEmpty -> criteriaBuilder.isNull(expression)
            else -> throw IllegalArgumentException("Invalid specification type")
        }
    }
}