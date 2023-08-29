package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

@Component
class ProjectSpecificationBuilder : SpecificationBuilder<Project, ProjectDatabaseModel>() {

    override val filterPredicates = filterPredicates<ProjectDatabaseModel> {
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
                val list = value as List<String>
                root.get<String>("title").`in`(list)
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
        field("targetLanguages") {
            all { criteriaBuilder, _, root, value ->
                criteriaBuilder.and(
                    *(value as List<*>).map { criteriaBuilder.isMember(it, root.get<List<String>>("targetLanguages")) }
                        .toTypedArray()
                )
            }
            any { criteriaBuilder, _, root, value ->
                criteriaBuilder.or(
                    *(value as List<*>).map { criteriaBuilder.isMember(it, root.get<List<String>>("targetLanguages")) }
                        .toTypedArray()
                )
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.and(
                    *(value as List<*>).map {
                        criteriaBuilder.isNotMember(
                            it,
                            root.get<List<String>>("targetLanguages")
                        )
                    }
                        .toTypedArray()
                )
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<List<String>>("targetLanguages"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.equal(root.get<List<String>>("targetLanguages"), emptyList<String>())
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
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<Int>("amount"), value as Int)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<Int>("amount"), value as Int)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<Int>("amount"), value as Int)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<Int>("amount"), value as Int)
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
        field("internalDeadline") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<ZonedDateTime>("internalDeadline"), value)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<ZonedDateTime>("internalDeadline"), value as ZonedDateTime)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<ZonedDateTime>("internalDeadline"), value as ZonedDateTime)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<ZonedDateTime>("internalDeadline"), value as ZonedDateTime)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(
                    root.get<ZonedDateTime>("internalDeadline"),
                    value as ZonedDateTime
                )
            }
            any { criteriaBuilder, _, root, value ->
                root.get<ZonedDateTime>("internalDeadline").`in`(value as List<ZonedDateTime>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<ZonedDateTime>("internalDeadline").`in`(value as List<ZonedDateTime>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("internalDeadline"))
            }
        }
        field("externalDeadline") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<ZonedDateTime>("externalDeadline"), value)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<ZonedDateTime>("externalDeadline"), value as ZonedDateTime)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<ZonedDateTime>("externalDeadline"), value as ZonedDateTime)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<ZonedDateTime>("externalDeadline"), value as ZonedDateTime)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(
                    root.get<ZonedDateTime>("externalDeadline"),
                    value as ZonedDateTime
                )
            }
            any { criteriaBuilder, _, root, value ->
                root.get<ZonedDateTime>("externalDeadline").`in`(value as List<ZonedDateTime>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<ZonedDateTime>("externalDeadline").`in`(value as List<ZonedDateTime>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("externalDeadline"))
            }
        }
        field("budget") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<BigDecimal>("budget"), value)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get("budget"), value as BigDecimal)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get("budget"), value as BigDecimal)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get("budget"), value as BigDecimal)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("budget"), value as BigDecimal)
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
                criteriaBuilder.equal(root.get<ProjectStatusDatabaseModel>("status"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<ProjectStatusDatabaseModel>("status").`in`(value as List<ProjectStatusDatabaseModel>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(
                    root.get<ProjectStatusDatabaseModel>("status").`in`(value as List<ProjectStatusDatabaseModel>)
                )
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ProjectStatusDatabaseModel>("status"))
            }
        }
        field("clientId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("client.id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("client.id").`in`(value as List<UUID>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("client.id").`in`(value as List<UUID>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("client.id"))
            }
        }
    }
}
