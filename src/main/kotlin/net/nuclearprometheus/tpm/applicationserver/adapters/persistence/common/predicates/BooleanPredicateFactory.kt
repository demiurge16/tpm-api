package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.predicates

import jakarta.persistence.criteria.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.BooleanSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class BooleanPredicateFactory<TEntity : Any, TDatabaseModel : Any>(
    private val expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<Boolean>
) : PredicateFactory<TEntity, TDatabaseModel>() {

    override fun createPredicate(
        root: Root<TDatabaseModel>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder,
        specification: Specification.ParameterizedSpecification<TEntity>
    ): Predicate {
        val expression = expressionSupplier(root, query, criteriaBuilder)
        return when (specification) {
            is BooleanSpecification.Eq -> criteriaBuilder.equal(expression, specification.value)
            is BooleanSpecification.IsNull -> criteriaBuilder.isNull(expression)
            else -> throw IllegalArgumentException("Invalid specification type")
        }
    }
}