package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operation
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class ClientSpecificationBuilder {

    fun build(query: Query<Client>): Specification<ClientDatabaseModel> {
        return Specification { root, criteriaQuery, criteriaBuilder ->
            buildPredicate(query.search.operationStack, root, criteriaQuery, criteriaBuilder)
        }
    }

    fun buildPredicate(
        operations: List<Operation<Client>>,
        root: Root<ClientDatabaseModel>,
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
                is Operation.Equals -> {
                    val (field, _, value) = operation.filter
                    val predicate = criteriaBuilder.equal(root.get<String>(field), value)
                    predicates.add(predicate)
                }
                is Operation.Contains -> {
                    val (field, _, value) = operation.filter
                    val predicate = criteriaBuilder.like(root.get(field), "%$value%")
                    predicates.add(predicate)
                }
                is Operation.GreaterThan -> {
                    val (field, _, value) = operation.filter
                    // TODO - Use comparables
                    val predicate = criteriaBuilder.greaterThan(root.get<Long>(field), value as Long)
                    predicates.add(predicate)
                }
                is Operation.LessThan -> {
                    val (field, _, value) = operation.filter
                    // TODO - Use comparables
                    val predicate = criteriaBuilder.lessThan(root.get<Long>(field), value as Long)
                    predicates.add(predicate)
                }
                is Operation.GreaterThanOrEqual -> {
                    val (field, _, value) = operation.filter
                    // TODO - Use comparables
                    val predicate = criteriaBuilder.greaterThanOrEqualTo(root.get<Long>(field), value as Long)
                    predicates.add(predicate)
                }
                is Operation.LessThanOrEqual -> {
                    val (field, _, value) = operation.filter
                    // TODO - Use comparables
                    val predicate = criteriaBuilder.lessThanOrEqualTo(root.get<Long>(field), value as Long)
                    predicates.add(predicate)
                }
                is Operation.AllElements -> {
                    // Handle all operation
                }
                is Operation.AnyElement -> {
                    // Handle any operation
                }
                is Operation.NoneElement -> {
                    // Handle none operation
                }
                is Operation.IsNull -> {
                    val (field) = operation.filter
                    val predicate = criteriaBuilder.isNull(root.get<String>(field))
                    predicates.add(predicate)
                }
                is Operation.IsEmpty -> {
                    val (field) = operation.filter
                    val predicate = criteriaBuilder.isEmpty(root.get(field))
                    predicates.add(predicate)
                }
                is Operation.Comparison -> {
                    throw IllegalArgumentException("Comparison operation is abstract and cannot be used directly")
                }
            }
        }

        return predicates.singleOrNull() ?: criteriaBuilder.conjunction()
    }
}
