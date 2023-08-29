package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.MeasurementDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import org.springframework.stereotype.Component
import java.util.*

@Component
class UnitSpecificationBuilder : SpecificationBuilder<Unit, UnitDatabaseModel>() {

    override val filterPredicates = filterPredicates<UnitDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("id").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("id").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        }
        field("name") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("name"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("name"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                root.get<String>("name").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<String>("name").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
        }
        field("volume") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Int>("volume"), value)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<Int>("volume"), value as Int)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<Int>("volume"), value as Int)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<Int>("volume"), value as Int)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<Int>("volume"), value as Int)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<Int>("volume").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<Int>("volume").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Int>("volume"))
            }
        }
        field("measurement") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<MeasurementDatabaseModel>("measurement"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<MeasurementDatabaseModel>("measurement").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<MeasurementDatabaseModel>("measurement").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<MeasurementDatabaseModel>("measurement"))
            }
        }
        field("active") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Boolean>("active"), value)
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        }
    }
}
