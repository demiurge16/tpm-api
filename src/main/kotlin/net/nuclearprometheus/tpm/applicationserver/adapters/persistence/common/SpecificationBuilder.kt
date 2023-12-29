package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification
import org.springframework.data.jpa.domain.Specification as SpringSpecification

// possible type combinations and filters:
// any unique singular token: eq, any, none, null
// any string type: eq, contains, any, none, null, empty
// any number type: eq, gt, gte, lt, lte, any, none, null
// any date type: eq, gt, gte, lt, lte, any, none, null
// any boolean type: eq, null
// any enumerated type: eq, any, none, null
// any collection type: all, any, none, null, empty
// leave the possibility to add custom filters

abstract class SpecificationBuilder<TEntity : Any, TDatabaseModel : Any> {

    fun build(query: Query<TEntity>): SpringSpecification<TDatabaseModel> {
        return SpringSpecification { root, criteriaQuery, criteriaBuilder ->
            buildPredicate(query.specification, root, criteriaQuery, criteriaBuilder)
        }
    }

    private fun buildPredicate(
        specification: Specification<TEntity>,
        root: Root<TDatabaseModel>,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return when (specification) {
            is Specification.AndSpecification -> {
                val left = buildPredicate(specification.left, root, criteriaQuery, criteriaBuilder)
                val right = buildPredicate(specification.right, root, criteriaQuery, criteriaBuilder)
                criteriaBuilder.and(left, right)
            }
            is Specification.OrSpecification -> {
                val left = buildPredicate(specification.left, root, criteriaQuery, criteriaBuilder)
                val right = buildPredicate(specification.right, root, criteriaQuery, criteriaBuilder)
                criteriaBuilder.or(left, right)
            }
            is Specification.NotSpecification -> {
                val spec = buildPredicate(specification.specification, root, criteriaQuery, criteriaBuilder)
                criteriaBuilder.not(spec)
            }
            is Specification.TrueSpecification -> {
                criteriaBuilder.isTrue(criteriaBuilder.literal(true))
            }
            is Specification.FalseSpecification -> {
                criteriaBuilder.isFalse(criteriaBuilder.literal(false))
            }
            is Specification.ParameterizedSpecification -> {
                filterPredicates[specification.name].createPredicate(root, criteriaQuery, criteriaBuilder, specification)
            }
        }
    }

    abstract val filterPredicates: FilterPredicates<TEntity, TDatabaseModel>
}