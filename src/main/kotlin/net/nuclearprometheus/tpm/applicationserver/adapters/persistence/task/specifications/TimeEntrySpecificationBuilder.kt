package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeEntryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeEntryStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeUnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class TimeEntrySpecificationBuilder : SpecificationBuilder<TimeEntry, TimeEntryDatabaseModel>() {

    override val filterPredicates = filterPredicates<TimeEntryDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                val id = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("id"), id)
            }
            any { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("id").`in`(ids)
            }
            none { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("id").`in`(ids))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        }
        field("description") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("description"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get("description"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                root.get<String>("description").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                criteriaBuilder.not(root.get<String>("description").`in`(values))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("description"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.equal(root.get<String>("description"), "")
            }
        }
        field("date") {
            eq { criteriaBuilder, _, root, value ->
                val date = LocalDate.parse(value as String)
                criteriaBuilder.equal(root.get<LocalDate>("date"), date)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val date = LocalDate.parse(value as String)
                criteriaBuilder.lessThan(root.get("date"), date)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val date = LocalDate.parse(value as String)
                criteriaBuilder.greaterThan(root.get("date"), date)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val date = LocalDate.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("date"), date)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val date = LocalDate.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("date"), date)
            }
            any { criteriaBuilder, _, root, value ->
                val dates = (value as List<String>).map { LocalDate.parse(it) }
                root.get<LocalDate>("date").`in`(dates)
            }
            none { criteriaBuilder, _, root, value ->
                val dates = (value as List<String>).map { LocalDate.parse(it) }
                criteriaBuilder.not(root.get<LocalDate>("date").`in`(dates))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<LocalDate>("date"))
            }
        }
        field("timeSpent") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Int>("timeSpent"), (value as String).toInt())
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get("timeSpent"), (value as String).toInt())
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get("timeSpent"), (value as String).toInt())
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("timeSpent"), (value as String).toInt())
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get("timeSpent"), (value as String).toInt())
            }
            any { criteriaBuilder, _, root, value ->
                root.get<Int>("timeSpent").`in`((value as List<String>).map { it.toInt() })
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<Int>("timeSpent").`in`((value as List<String>).map { it.toInt() }))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Int>("timeSpent"))
            }
        }
        field("timeUnit") {
            eq { criteriaBuilder, _, root, value ->
                val timeUnit = TimeUnitDatabaseModel.valueOf(value as String)
                criteriaBuilder.equal(root.get<TimeUnitDatabaseModel>("timeUnit"), timeUnit)
            }
            any { criteriaBuilder, _, root, value ->
                val timeUnits = (value as List<String>).map { TimeUnitDatabaseModel.valueOf(it) }
                root.get<TimeUnitDatabaseModel>("timeUnit").`in`(timeUnits)
            }
            none { criteriaBuilder, _, root, value ->
                val timeUnits = (value as List<String>).map { TimeUnitDatabaseModel.valueOf(it) }
                criteriaBuilder.not(root.get<TimeUnitDatabaseModel>("timeUnit").`in`(timeUnits))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<TimeUnitDatabaseModel>("timeUnit"))
            }
        }
        field("status") {
            eq { criteriaBuilder, _, root, value ->
                val status = TimeEntryStatusDatabaseModel.valueOf(value as String)
                criteriaBuilder.equal(root.get<TimeEntryStatusDatabaseModel>("status"), status)
            }
            any { criteriaBuilder, _, root, value ->
                val statuses = (value as List<String>).map { TimeEntryStatusDatabaseModel.valueOf(it) }
                root.get<TimeEntryStatusDatabaseModel>("status").`in`(statuses)
            }
            none { criteriaBuilder, _, root, value ->
                val statuses = (value as List<String>).map { TimeEntryStatusDatabaseModel.valueOf(it) }
                criteriaBuilder.not(root.get<TimeEntryStatusDatabaseModel>("status").`in`(statuses))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<TimeEntryStatusDatabaseModel>("status"))
            }
        }
        field("taskId") {
            eq { criteriaBuilder, _, root, value ->
                val taskId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("taskId"), taskId)
            }
            any { criteriaBuilder, _, root, value ->
                val taskIds = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("taskId").`in`(taskIds)
            }
            none { criteriaBuilder, _, root, value ->
                val taskIds = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("taskId").`in`(taskIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("taskId"))
            }
        }
    }
}