package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Operation
import org.springframework.data.jpa.domain.Specification

// possible type combinations and filters:
// any unique singular token: eq, any, none, null
// any string type: eq, contains, any, none, null, empty
// any number type: eq, gt, gte, lt, lte, any, none, null
// any date type: eq, gt, gte, lt, lte, any, none, null
// any boolean type: eq, null
// any enumerated type: eq, any, none, null
// any collection type: all, any, none, null, empty
// leave the possibility to add custom filters

typealias PredicateSupplier<T> = (CriteriaBuilder, CriteriaQuery<*>, Root<T>, Any) -> Predicate

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
                    val predicateSupplier = filterPredicates[field, op.symbol]
                    predicates.add(predicateSupplier(criteriaBuilder, criteriaQuery, root, value))
                }
            }
        }

        return predicates.singleOrNull() ?: criteriaBuilder.conjunction()
    }

    abstract val filterPredicates: FilterPredicates<TDatabaseModel>
}