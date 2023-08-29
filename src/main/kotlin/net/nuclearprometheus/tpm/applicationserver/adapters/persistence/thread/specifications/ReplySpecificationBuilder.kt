package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.UUID

@Component
class ReplySpecificationBuilder : SpecificationBuilder<Reply, ReplyDatabaseModel>() {

    override val filterPredicates = filterPredicates<ReplyDatabaseModel> {
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
        field("createdAt") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<ZonedDateTime>("createdAt"), value)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<ZonedDateTime>("createdAt"), value as ZonedDateTime)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<ZonedDateTime>("createdAt"), value as ZonedDateTime)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<ZonedDateTime>("createdAt"), value as ZonedDateTime)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<ZonedDateTime>("createdAt"), value as ZonedDateTime)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<ZonedDateTime>("createdAt").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<ZonedDateTime>("createdAt").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("createdAt"))
            }
        }
        field("deleted") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Boolean>("deleted"), value)
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Boolean>("deleted"))
            }
        }
        field("authorId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("authorId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("authorId").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("authorId").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("authorId"))
            }
        }
        field("parentReplyId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("parentReplyId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("parentReplyId").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("parentReplyId").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("parentReplyId"))
            }
        }
        field("threadId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("threadId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("threadId").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("threadId").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("threadId"))
            }
        }
    }
}
