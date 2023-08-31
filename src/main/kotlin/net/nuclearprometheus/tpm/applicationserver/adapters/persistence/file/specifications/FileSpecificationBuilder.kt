package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.entities.FileDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class FileSpecificationBuilder : SpecificationBuilder<File, FileDatabaseModel>() {

    override val filterPredicates = filterPredicates<FileDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                val uuid = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("id").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("id").`in`(list))
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
                criteriaBuilder.equal(root.get<String>("name"), "")
            }
        }
        field("type") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("type"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get("type"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                root.get<String>("type").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<String>("type").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("type"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.equal(root.get<String>("type"), "")
            }
        }
        field("location") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("location"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get("location"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                root.get<String>("location").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<String>("location").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("location"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.equal(root.get<String>("location"), "")
            }
        }
        field("size") {
            eq { criteriaBuilder, _, root, value ->
                val size = (value as String).toLong()
                criteriaBuilder.equal(root.get<Long>("size"), size)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val size = (value as String).toLong()
                criteriaBuilder.greaterThan(root.get("size"), size)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val size = (value as String).toLong()
                criteriaBuilder.lessThan(root.get("size"), size)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val size = (value as String).toLong()
                criteriaBuilder.greaterThanOrEqualTo(root.get("size"), size)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val size = (value as String).toLong()
                criteriaBuilder.lessThanOrEqualTo(root.get("size"), size)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { it.toLong() }
                root.get<Long>("size").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { it.toLong() }
                criteriaBuilder.not(root.get<Long>("size").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Long>("size"))
            }
        }
        field("uploadTime") {
            eq { criteriaBuilder, _, root, value ->
                val uploadTime = ZonedDateTime.parse(value as String)
                criteriaBuilder.equal(root.get<ZonedDateTime>("uploadTime"), uploadTime)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val uploadTime = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThan(root.get("uploadTime"), uploadTime)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val uploadTime = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThan(root.get("uploadTime"), uploadTime)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val uploadTime = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("uploadTime"), uploadTime)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val uploadTime = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("uploadTime"), uploadTime)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("uploadTime").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("uploadTime").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("uploadTime"))
            }
        }
        field("uploaderId") {
            eq { criteriaBuilder, _, root, value ->
                val uploaderId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("uploaderId"), uploaderId)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("uploaderId").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("uploaderId").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("uploaderId"))
            }
        }
        field("projectId") {
            eq { criteriaBuilder, _, root, value ->
                val projectId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("projectId"), projectId)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("projectId").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("projectId").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("projectId"))
            }
        }
    }
}
