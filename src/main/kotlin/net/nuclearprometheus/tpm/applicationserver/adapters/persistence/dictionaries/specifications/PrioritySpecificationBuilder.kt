package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.PriorityDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority
import org.springframework.stereotype.Component
import java.util.*

@Component
class PrioritySpecificationBuilder : SpecificationBuilder<Priority, PriorityDatabaseModel>() {

    override val filters = mapOf(
        "id" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                root.get<UUID>("id").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<UUID>("id").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        ),
        "name" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("name"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("name"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                root.get<String>("name").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("name").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
        ),
        "value" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<Int>("value"), value)
            },
            "gt" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.greaterThan(root.get<Int>("value"), value as Int)
            },
            "lt" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.lessThan(root.get<Int>("value"), value as Int)
            },
            "gte" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<Int>("value"), value as Int)
            },
            "lte" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.lessThanOrEqualTo(root.get<Int>("value"), value as Int)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                root.get<Int>("value").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<Int>("value").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<Int>("value"))
            }
        ),
        "active" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<Boolean>("active"), value)
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<PriorityDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        )
    )
}