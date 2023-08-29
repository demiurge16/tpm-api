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
                val uuid = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("id"), uuid)
            }
            any { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                root.get<UUID>("id").`in`(list.map { UUID.fromString(it) })
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<UUID>("id").`in`(list.map { UUID.fromString(it) }))
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
                criteriaBuilder.like(root.get("name"), "%$value%")
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
                criteriaBuilder.equal(root.get<Int>("volume"), (value as String).toInt())
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get("volume"), (value as String).toInt())
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get("volume"), (value as String).toInt())
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("volume"), (value as String).toInt())
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get("volume"), (value as String).toInt())
            }
            any { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                root.get<Int>("volume").`in`(list.map { it.toInt() })
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<Int>("volume").`in`(list.map { it.toInt() }))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Int>("volume"))
            }
        }
        field("measurement") {
            eq { criteriaBuilder, _, root, value ->
                val measurement = MeasurementDatabaseModel.valueOf(value as String)
                criteriaBuilder.equal(root.get<MeasurementDatabaseModel>("measurement"), measurement)
            }
            any { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                root.get<MeasurementDatabaseModel>("measurement").`in`(list.map { MeasurementDatabaseModel.valueOf(it) })
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<MeasurementDatabaseModel>("measurement").`in`(list.map { MeasurementDatabaseModel.valueOf(it) }))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<MeasurementDatabaseModel>("measurement"))
            }
        }
        field("active") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Boolean>("active"), (value as String).toBoolean())
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        }
    }
}
