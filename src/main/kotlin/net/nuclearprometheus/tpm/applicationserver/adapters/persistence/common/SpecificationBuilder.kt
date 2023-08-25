package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operation
import org.springframework.data.jpa.domain.Specification

typealias PredicateSupplier<T> = (CriteriaBuilder, Root<T>, Any) -> Predicate

abstract class SpecificationBuilder<TEntity : Any, TDatabaseModel : Any> {

    fun build(query: Query<TEntity>): Specification<TDatabaseModel> {
        return Specification { root, criteriaQuery, criteriaBuilder ->
            buildPredicate(query.search.operationStack, root, criteriaQuery, criteriaBuilder)
        }
    }

    private fun buildPredicate(
        operations: List<Operation<TEntity>>,
        root: Root<TDatabaseModel>,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val predicates = mutableListOf<Predicate>()
        for (operation in operations) {
            when (operation) {
                is Operation.And -> {
                    val predicate = criteriaBuilder.and(predicates.removeLast(), predicates.removeLast())
                    predicates.add(predicate)
                }
                is Operation.Or -> {
                    val predicate = criteriaBuilder.or(predicates.removeLast(), predicates.removeLast())
                    predicates.add(predicate)
                }
                is Operation.Not -> {
                    val predicate = criteriaBuilder.not(predicates.removeLast())
                    predicates.add(predicate)
                }
                is Operation.Comparison -> {
                    val (field, op, value) = operation.filter
                    val predicateSupplier = filters[field]?.get(op.symbol) ?: throw Exception("Unknown field")
                    predicates.add(predicateSupplier(criteriaBuilder, root, value))
                }
            }
        }

        return predicates.singleOrNull() ?: criteriaBuilder.conjunction()
    }

    abstract val filters: Map<String, Map<String, PredicateSupplier<TDatabaseModel>>>
}
