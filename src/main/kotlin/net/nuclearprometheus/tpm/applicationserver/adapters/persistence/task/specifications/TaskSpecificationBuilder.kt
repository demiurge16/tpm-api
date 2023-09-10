package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.AccuracyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.IndustryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.PriorityDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Component
class TaskSpecificationBuilder : SpecificationBuilder<Task, TaskDatabaseModel>() {

    override val filterPredicates = filterPredicates<TaskDatabaseModel> {
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
        field("title") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("title"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get("title"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                root.get<String>("title").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                criteriaBuilder.not(root.get<String>("title").`in`(values))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("title"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.equal(root.get<String>("title"), "")
            }
        }
        field("sourceLanguage") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("sourceLanguage"), value)
            }
            any { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                root.get<String>("sourceLanguage").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                criteriaBuilder.not(root.get<String>("sourceLanguage").`in`(values))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("sourceLanguage"))
            }
        }
        field("targetLanguage") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("targetLanguage"), value)
            }
            any { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                root.get<String>("targetLanguage").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                criteriaBuilder.not(root.get<String>("targetLanguage").`in`(values))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("targetLanguage"))
            }
        }
        field("accuracyId") {
            eq { criteriaBuilder, _, root, value ->
                val accuracyId = UUID.fromString(value as String)
                val accuracy = root.join<TaskDatabaseModel, AccuracyDatabaseModel>("accuracy")
                criteriaBuilder.equal(accuracy.get<UUID>("id"), accuracyId)
            }
            any { criteriaBuilder, _, root, value ->
                val accuracyIds = (value as List<String>).map { UUID.fromString(it) }
                val accuracy = root.join<TaskDatabaseModel, AccuracyDatabaseModel>("accuracy")
                accuracy.get<UUID>("id").`in`(accuracyIds)
            }
            none { criteriaBuilder, _, root, value ->
                val accuracyIds = (value as List<String>).map { UUID.fromString(it) }
                val accuracy = root.join<TaskDatabaseModel, AccuracyDatabaseModel>("accuracy")
                criteriaBuilder.not(accuracy.get<UUID>("id").`in`(accuracyIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                val accuracy = root.join<TaskDatabaseModel, AccuracyDatabaseModel>("accuracy")
                criteriaBuilder.isNull(accuracy.get<UUID>("id"))
            }
        }
        field("industryId") {
            eq { criteriaBuilder, _, root, value ->
                val industryId = UUID.fromString(value as String)
                val industry = root.join<TaskDatabaseModel, IndustryDatabaseModel>("industry")
                criteriaBuilder.equal(industry.get<UUID>("id"), industryId)
            }
            any { criteriaBuilder, _, root, value ->
                val industryIds = (value as List<String>).map { UUID.fromString(it) }
                val industry = root.join<TaskDatabaseModel, IndustryDatabaseModel>("industry")
                industry.get<UUID>("id").`in`(industryIds)
            }
            none { criteriaBuilder, _, root, value ->
                val industryIds = (value as List<String>).map { UUID.fromString(it) }
                val industry = root.join<TaskDatabaseModel, IndustryDatabaseModel>("industry")
                criteriaBuilder.not(industry.get<UUID>("id").`in`(industryIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                val industry = root.join<TaskDatabaseModel, IndustryDatabaseModel>("industry")
                criteriaBuilder.isNull(industry.get<UUID>("id"))
            }
        }
        field("unitId") {
            eq { criteriaBuilder, _, root, value ->
                val unitId = UUID.fromString(value as String)
                val unit = root.join<TaskDatabaseModel, UnitDatabaseModel>("unit")
                criteriaBuilder.equal(unit.get<UUID>("id"), unitId)
            }
            any { criteriaBuilder, _, root, value ->
                val unitIds = (value as List<String>).map { UUID.fromString(it) }
                val unit = root.join<TaskDatabaseModel, UnitDatabaseModel>("unit")
                unit.get<UUID>("id").`in`(unitIds)
            }
            none { criteriaBuilder, _, root, value ->
                val unitIds = (value as List<String>).map { UUID.fromString(it) }
                val unit = root.join<TaskDatabaseModel, UnitDatabaseModel>("unit")
                criteriaBuilder.not(unit.get<UUID>("id").`in`(unitIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                val unit = root.join<TaskDatabaseModel, UnitDatabaseModel>("unit")
                criteriaBuilder.isNull(unit.get<UUID>("id"))
            }
        }
        field("amount") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Int>("amount"), (value as String).toInt())
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get("amount"), (value as String).toInt())
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get("amount"), (value as String).toInt())
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), (value as String).toInt())
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get("amount"), (value as String).toInt())
            }
            any { criteriaBuilder, _, root, value ->
                root.get<Int>("amount").`in`((value as List<String>).map { it.toInt() })
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<Int>("amount").`in`((value as List<String>).map { it.toInt() }))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Int>("amount"))
            }
        }
        field("expectedStart") {
            eq { criteriaBuilder, _, root, value ->
                val expectedStart = ZonedDateTime.parse(value as String)
                criteriaBuilder.equal(root.get<ZonedDateTime>("expectedStart"), expectedStart)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val expectedStart = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThan(root.get("expectedStart"), expectedStart)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val expectedStart = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThan(root.get("expectedStart"), expectedStart)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val expectedStart = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("expectedStart"), expectedStart)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val expectedStart = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("expectedStart"), expectedStart)
            }
            any { criteriaBuilder, _, root, value ->
                val expectedStarts = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("expectedStart").`in`(expectedStarts)
            }
            none { criteriaBuilder, _, root, value ->
                val expectedStarts = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("expectedStart").`in`(expectedStarts))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("expectedStart"))
            }
        }
        field("deadline") {
            eq { criteriaBuilder, _, root, value ->
                val deadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.equal(root.get<ZonedDateTime>("deadline"), deadline)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val deadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThan(root.get("deadline"), deadline)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val deadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThan(root.get("deadline"), deadline)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val deadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("deadline"), deadline)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val deadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("deadline"), deadline)
            }
            any { criteriaBuilder, _, root, value ->
                val deadlines = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("deadline").`in`(deadlines)
            }
            none { criteriaBuilder, _, root, value ->
                val deadlines = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("deadline").`in`(deadlines))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("deadline"))
            }
        }
        field("budget") {
            eq { criteriaBuilder, _, root, value ->
                val budget = BigDecimal(value as String)
                criteriaBuilder.equal(root.get<BigDecimal>("budget"), budget)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val budget = BigDecimal(value as String)
                criteriaBuilder.lessThan(root.get("budget"), budget)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val budget = BigDecimal(value as String)
                criteriaBuilder.greaterThan(root.get("budget"), budget)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val budget = BigDecimal(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("budget"), budget)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val budget = BigDecimal(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("budget"), budget)
            }
            any { criteriaBuilder, _, root, value ->
                val budgets = (value as List<String>).map { BigDecimal(it) }
                root.get<BigDecimal>("budget").`in`(budgets)
            }
            none { criteriaBuilder, _, root, value ->
                val budgets = (value as List<String>).map { BigDecimal(it) }
                criteriaBuilder.not(root.get<BigDecimal>("budget").`in`(budgets))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<BigDecimal>("budget"))
            }
        }
        field("currency") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("currency"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("currency").`in`(value as List<String>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("currency").`in`(value as List<String>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("currency"))
            }
        }
        field("status") {
            eq { criteriaBuilder, _, root, value ->
                val status = TaskStatusDatabaseModel.valueOf(value as String)
                criteriaBuilder.equal(root.get<TaskStatusDatabaseModel>("status"), status)
            }
            any { criteriaBuilder, _, root, value ->
                val statuses = (value as List<String>).map { TaskStatusDatabaseModel.valueOf(it) }
                root.get<TaskStatusDatabaseModel>("status").`in`(statuses)
            }
            none { criteriaBuilder, _, root, value ->
                val statuses = (value as List<String>).map { TaskStatusDatabaseModel.valueOf(it) }
                criteriaBuilder.not(root.get<TaskStatusDatabaseModel>("status").`in`(statuses))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<TaskStatusDatabaseModel>("status"))
            }
        }
        field("priorityId") {
            eq { criteriaBuilder, _, root, value ->
                val priorityId = UUID.fromString(value as String)
                val priority = root.join<TaskDatabaseModel, PriorityDatabaseModel>("priority")
                criteriaBuilder.equal(priority.get<UUID>("id"), priorityId)
            }
            any { criteriaBuilder, _, root, value ->
                val priorityIds = (value as List<String>).map { UUID.fromString(it) }
                val priority = root.join<TaskDatabaseModel, PriorityDatabaseModel>("priority")
                priority.get<UUID>("id").`in`(priorityIds)
            }
            none { criteriaBuilder, _, root, value ->
                val priorityIds = (value as List<String>).map { UUID.fromString(it) }
                val priority = root.join<TaskDatabaseModel, PriorityDatabaseModel>("priority")
                criteriaBuilder.not(priority.get<UUID>("id").`in`(priorityIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                val priority = root.join<TaskDatabaseModel, PriorityDatabaseModel>("priority")
                criteriaBuilder.isNull(priority.get<UUID>("id"))
            }
        }
        field("assigneeId") {
            eq { criteriaBuilder, _, root, value ->
                val assigneeId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("assigneeId"), assigneeId)
            }
            any { criteriaBuilder, _, root, value ->
                val assigneeIds = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("assigneeId").`in`(assigneeIds)
            }
            none { criteriaBuilder, _, root, value ->
                val assigneeIds = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("assigneeId").`in`(assigneeIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("assigneeId"))
            }
        }
        field("projectId") {
            eq { criteriaBuilder, _, root, value ->
                val projectId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("projectId"), projectId)
            }
            any { criteriaBuilder, _, root, value ->
                val projectIds = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("projectId").`in`(projectIds)
            }
            none { criteriaBuilder, _, root, value ->
                val projectIds = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("projectId").`in`(projectIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("projectId"))
            }
        }
    }
}