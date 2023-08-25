package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.AccuracyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import org.springframework.stereotype.Component
import java.util.*

// possible type combinations and filters:
// any string type: eq, contains, any, none, null, empty
// any number type: eq, gt, gte, lt, lte, any, none, null
// any date type: eq, gt, gte, lt, lte, any, none, null
// any boolean type: eq, null
// any enum type: eq, any, none, null
// any other non-collection type: eq, any, none, null
// any collection type: all, any, none, null, empty
// leave the possibility to add custom filters

@Component
class AccuracySpecificationBuilder : SpecificationBuilder<Accuracy, AccuracyDatabaseModel>() {

    override val filters = mapOf(
        "id" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, value: Any ->
                root.get<UUID>("id").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<UUID>("id").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        ),
        "name" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("name"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("name"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, value: Any ->
                root.get<String>("name").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("name").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
        ),
        "active" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<Boolean>("active"), value)
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<AccuracyDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        )
    )
}
