package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

@Component
class TaskSpecificationBuilder : SpecificationBuilder<Task, TaskDatabaseModel>() {

    override val filterPredicates = filterPredicates<TaskDatabaseModel> {
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
        field("title") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("title"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("title"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("title").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("title").`in`(value))
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
                root.get<String>("sourceLanguage").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("sourceLanguage").`in`(value))
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
                root.get<String>("targetLanguage").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("targetLanguage").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("targetLanguage"))
            }
        }
        field("accuracyId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("accuracy.id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("accuracy.id").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("accuracy.id").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("accuracy.id"))
            }
        }
        field("industryId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("industry.id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("industry.id").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("industry.id").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("industry.id"))
            }
        }
        field("unitId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("unit.id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("unit.id").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("unit.id").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("unit.id"))
            }
        }
        field("amount") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Int>("amount"), value)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<Int>("amount"), (value as String).toInt())
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<Int>("amount"), (value as String).toInt())
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<Int>("amount"), (value as String).toInt())
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<Int>("amount"), (value as String).toInt())
            }
            any { criteriaBuilder, _, root, value ->
                root.get<Int>("amount").`in`(value as List<Int>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<Int>("amount").`in`(value as List<Int>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Int>("amount"))
            }
        }
        field("expectedStart") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<ZonedDateTime>("expectedStart"), value)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<ZonedDateTime>("expectedStart"), value as ZonedDateTime)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<ZonedDateTime>("expectedStart"), value as ZonedDateTime)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<ZonedDateTime>("expectedStart"), value as ZonedDateTime)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<ZonedDateTime>("expectedStart"), value as ZonedDateTime)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<ZonedDateTime>("expectedStart").`in`(value as List<ZonedDateTime>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<ZonedDateTime>("expectedStart").`in`(value as List<ZonedDateTime>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("expectedStart"))
            }
        }
        field("deadline") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<ZonedDateTime>("deadline"), value)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<ZonedDateTime>("deadline"), value as ZonedDateTime)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<ZonedDateTime>("deadline"), value as ZonedDateTime)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<ZonedDateTime>("deadline"), value as ZonedDateTime)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<ZonedDateTime>("deadline"), value as ZonedDateTime)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<ZonedDateTime>("deadline").`in`(value as List<ZonedDateTime>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<ZonedDateTime>("deadline").`in`(value as List<ZonedDateTime>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("deadline"))
            }
        }
        field("budget") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<BigDecimal>("budget"), value)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<BigDecimal>("budget"), value as BigDecimal)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<BigDecimal>("budget"), value as BigDecimal)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<BigDecimal>("budget"), value as BigDecimal)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<BigDecimal>("budget"), value as BigDecimal)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<BigDecimal>("budget").`in`(value as List<BigDecimal>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<BigDecimal>("budget").`in`(value as List<BigDecimal>))
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
                criteriaBuilder.equal(root.get<TaskStatusDatabaseModel>("status"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<TaskStatusDatabaseModel>("status").`in`(value as List<TaskStatusDatabaseModel>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(
                    root.get<TaskStatusDatabaseModel>("status").`in`(value as List<TaskStatusDatabaseModel>)
                )
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<TaskStatusDatabaseModel>("status"))
            }
        }
        field("priority") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("priority.id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("priority.id").`in`(value as List<UUID>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("priority.id").`in`(value as List<UUID>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("priority.id"))
            }
        }
        field("assigneeId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("assigneeId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("assigneeId").`in`(value as List<UUID>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("assigneeId").`in`(value as List<UUID>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("assigneeId"))
            }
        }
        field("projectId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("projectId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("projectId").`in`(value as List<UUID>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("projectId").`in`(value as List<UUID>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("projectId"))
            }
        }
    }
}