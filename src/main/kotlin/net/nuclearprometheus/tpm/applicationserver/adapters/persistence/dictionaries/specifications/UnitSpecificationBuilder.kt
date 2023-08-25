package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.MeasurementDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import org.springframework.stereotype.Component
import java.util.*

@Component
class UnitSpecificationBuilder : SpecificationBuilder<Unit, UnitDatabaseModel>() {

    override val filters = mapOf(
        "id" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                root.get<UUID>("id").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<UUID>("id").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        ),
        "name" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("name"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("name"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                root.get<String>("name").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("name").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
        ),
        "volume" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<Int>("volume"), value)
            },
            "gt" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.greaterThan(root.get<Int>("volume"), value as Int)
            },
            "lt" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.lessThan(root.get<Int>("volume"), value as Int)
            },
            "gte" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<Int>("volume"), value as Int)
            },
            "lte" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.lessThanOrEqualTo(root.get<Int>("volume"), value as Int)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                root.get<Int>("volume").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<Int>("volume").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<Int>("volume"))
            }
        ),
        "measurement" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<MeasurementDatabaseModel>("measurement"), value)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                root.get<MeasurementDatabaseModel>("measurement").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<MeasurementDatabaseModel>("measurement").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<MeasurementDatabaseModel>("measurement"))
            }
        ),
        "active" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<Boolean>("active"), value)
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<UnitDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        )
    )
}