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
                val list = value as List<String>
                root.get<String>("title").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<String>("title").`in`(list))
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
                val list = value as List<String>
                root.get<String>("sourceLanguage").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<String>("sourceLanguage").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("sourceLanguage"))
            }
        }
        field("targetLanguages") {
            all { criteriaBuilder, _, root, value ->
                val targetLanguages = value as List<String>
                criteriaBuilder.and(
                    *targetLanguages.map { criteriaBuilder.isMember(it, root.get<List<String>>("targetLanguages")) }
                        .toTypedArray()
                )
            }
            any { criteriaBuilder, _, root, value ->
                val targetLanguages = value as List<String>
                criteriaBuilder.or(
                    *targetLanguages.map { criteriaBuilder.isMember(it, root.get<List<String>>("targetLanguages")) }
                        .toTypedArray()
                )
            }
            none { criteriaBuilder, _, root, value ->
                val targetLanguages = value as List<String>
                criteriaBuilder.and(
                    *targetLanguages
                        .map {
                            criteriaBuilder.isNotMember(it, root.get<List<String>>("targetLanguages"))
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
                val id = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("accuracy.id"), id)
            }
            any { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("accuracy.id").`in`(ids)
            }
            none { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("accuracy.id").`in`(ids))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("accuracy.id"))
            }
        }
        field("industryId") {
            eq { criteriaBuilder, _, root, value ->
                val id = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("industry.id"), id)
            }
            any { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("industry.id").`in`(ids)
            }
            none { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("industry.id").`in`(ids))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("industry.id"))
            }
        }
        field("unitId") {
            eq { criteriaBuilder, _, root, value ->
                val id = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("unit.id"), id)
            }
            any { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("unit.id").`in`(ids)
            }
            none { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("unit.id").`in`(ids))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("unit.id"))
            }
        }
        field("amount") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Int>("amount"), (value as String).toInt())
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get("amount"), (value as String).toInt())
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get("amount"), (value as String).toInt())
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get("amount"), (value as String).toInt())
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), (value as String).toInt())
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { it.toInt() }
                root.get<Int>("amount").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { it.toInt() }
                criteriaBuilder.not(root.get<Int>("amount").`in`(list))
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
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("expectedStart").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("expectedStart").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("expectedStart"))
            }
        }
        field("internalDeadline") {
            eq { criteriaBuilder, _, root, value ->
                val internalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.equal(root.get<ZonedDateTime>("internalDeadline"), internalDeadline)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val internalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThan(root.get("internalDeadline"), internalDeadline)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val internalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThan(root.get("internalDeadline"), internalDeadline)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val internalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("internalDeadline"), internalDeadline)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val internalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("internalDeadline"), internalDeadline)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("internalDeadline").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("internalDeadline").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("internalDeadline"))
            }
        }
        field("externalDeadline") {
            eq { criteriaBuilder, _, root, value ->
                val externalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.equal(root.get<ZonedDateTime>("externalDeadline"), externalDeadline)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val externalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThan(root.get("externalDeadline"), externalDeadline)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val externalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThan(root.get("externalDeadline"), externalDeadline)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val externalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("externalDeadline"), externalDeadline)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val externalDeadline = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("externalDeadline"), externalDeadline)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("externalDeadline").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("externalDeadline").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("externalDeadline"))
            }
        }
        field("budget") {
            eq { criteriaBuilder, _, root, value ->
                val budget = (value as String).toBigDecimal()
                criteriaBuilder.equal(root.get<BigDecimal>("budget"), budget)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val budget = (value as String).toBigDecimal()
                criteriaBuilder.lessThan(root.get("budget"), budget)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val budget = (value as String).toBigDecimal()
                criteriaBuilder.greaterThan(root.get("budget"), budget)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val budget = (value as String).toBigDecimal()
                criteriaBuilder.lessThanOrEqualTo(root.get("budget"), budget)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val budget = (value as String).toBigDecimal()
                criteriaBuilder.greaterThanOrEqualTo(root.get("budget"), budget)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { it.toBigDecimal() }
                root.get<BigDecimal>("budget").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { it.toBigDecimal() }
                criteriaBuilder.not(root.get<BigDecimal>("budget").`in`(list))
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
                val status = ProjectStatusDatabaseModel.valueOf(value as String)
                criteriaBuilder.equal(root.get<ProjectStatusDatabaseModel>("status"), status)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ProjectStatusDatabaseModel.valueOf(it) }
                root.get<ProjectStatusDatabaseModel>("status").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ProjectStatusDatabaseModel.valueOf(it) }
                criteriaBuilder.not(root.get<ProjectStatusDatabaseModel>("status").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ProjectStatusDatabaseModel>("status"))
            }
        }
        field("clientId") {
            eq { criteriaBuilder, _, root, value ->
                val clientId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("client.id"), clientId)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("client.id").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("client.id").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("client.id"))
            }
        }
    }
}
