package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.predicates

import jakarta.persistence.criteria.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.CollectionSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification

class CollectionPredicateFactory<TEntity : Any, TDatabaseModel : Any, TValue : Any>(
    private val expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<out Collection<TValue>>
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
            is CollectionSpecification.ContainsElement<*, *> -> {
                val value = specification.value as TValue
                criteriaBuilder.isMember(value, expression)
            }
            is CollectionSpecification.AllElement<*, *> -> {
                val value = specification.value as Collection<TValue>
                criteriaBuilder.and(
                    *value.map { criteriaBuilder.isMember(it, expression) }.toTypedArray()
                )
            }
            is CollectionSpecification.AnyElement<*, *> -> {
                val value = specification.value as Collection<TValue>
                criteriaBuilder.or(
                    *value.map { criteriaBuilder.isMember(it, expression) }.toTypedArray()
                )
            }
            is CollectionSpecification.NoneElement<*, *> -> {
                val value = specification.value as Collection<TValue>
                criteriaBuilder.not(
                    criteriaBuilder.or(
                        *value.map { criteriaBuilder.isMember(it, expression) }.toTypedArray()
                    )
                )
            }
            is CollectionSpecification.IsNull -> criteriaBuilder.isNull(expression)
            is CollectionSpecification.IsEmpty -> criteriaBuilder.isEmpty(expression)
            else -> throw IllegalArgumentException("Invalid specification type")
        }
    }
}