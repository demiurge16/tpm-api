package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.predicates

import jakarta.persistence.criteria.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.EnumSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class EnumPredicateFactory<TEntity : Any, TDatabaseModel : Any, TValue : Enum<TValue>>(
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
            is EnumSpecification.Eq<*, *> -> criteriaBuilder.equal(expression, specification.value)
            is EnumSpecification.AnyElement<*, *> -> expression.`in`(specification.value)
            is EnumSpecification.NoneElement<*, *> -> criteriaBuilder.not(expression.`in`(specification.value))
            is EnumSpecification.IsNull -> criteriaBuilder.isNull(expression)
            else -> throw IllegalArgumentException("Invalid specification type")
        }
    }
}