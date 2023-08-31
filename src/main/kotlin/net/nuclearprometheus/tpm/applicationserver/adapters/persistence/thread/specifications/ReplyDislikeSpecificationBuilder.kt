package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDislikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyDislike
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class ReplyDislikeSpecificationBuilder : SpecificationBuilder<ReplyDislike, ReplyDislikeDatabaseModel>() {

    override val filterPredicates = filterPredicates<ReplyDislikeDatabaseModel> {
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
        field("createdAt") {
            eq { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.equal(root.get<ZonedDateTime>("createdAt"), createdAt)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThan(root.get("createdAt"), createdAt)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThan(root.get("createdAt"), createdAt)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAt)
            }
            any { criteriaBuilder, _, root, value ->
                val createdAt = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("createdAt").`in`(createdAt)
            }
            none { criteriaBuilder, _, root, value ->
                val createdAt = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("createdAt").`in`(createdAt))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("createdAt"))
            }
        }
        field("authorId") {
            eq { criteriaBuilder, _, root, value ->
                val authorId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("authorId"), authorId)
            }
            any { criteriaBuilder, _, root, value ->
                val authorIds = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("authorId").`in`(authorIds)
            }
            none { criteriaBuilder, _, root, value ->
                val authorIds = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("authorId").`in`(authorIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("authorId"))
            }
        }
        field("replyId") {
            eq { criteriaBuilder, _, root, value ->
                val replyId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("replyId"), replyId)
            }
            any { criteriaBuilder, _, root, value ->
                val replyIds = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("replyId").`in`(replyIds)
            }
            none { criteriaBuilder, _, root, value ->
                val replyIds = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("replyId").`in`(replyIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("replyId"))
            }
        }
    }
}