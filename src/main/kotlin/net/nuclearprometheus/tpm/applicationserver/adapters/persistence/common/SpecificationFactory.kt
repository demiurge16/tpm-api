package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification
import org.springframework.data.jpa.domain.Specification as SpringSpecification

abstract class SpecificationFactory<TEntity : Any, TDatabaseModel : Any> {

    fun create(query: Query<TEntity>): SpringSpecification<TDatabaseModel> {
        return SpringSpecification { root, criteriaQuery, criteriaBuilder ->
            createPredicate(query.specification, root, criteriaQuery, criteriaBuilder)
        }
    }

    private fun createPredicate(
        specification: Specification<TEntity>,
        root: Root<TDatabaseModel>,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return when (specification) {
            is Specification.AndSpecification -> {
                val left = createPredicate(specification.left, root, criteriaQuery, criteriaBuilder)
                val right = createPredicate(specification.right, root, criteriaQuery, criteriaBuilder)
                criteriaBuilder.and(left, right)
            }
            is Specification.OrSpecification -> {
                val left = createPredicate(specification.left, root, criteriaQuery, criteriaBuilder)
                val right = createPredicate(specification.right, root, criteriaQuery, criteriaBuilder)
                criteriaBuilder.or(left, right)
            }
            is Specification.NotSpecification -> {
                val spec = createPredicate(specification.specification, root, criteriaQuery, criteriaBuilder)
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