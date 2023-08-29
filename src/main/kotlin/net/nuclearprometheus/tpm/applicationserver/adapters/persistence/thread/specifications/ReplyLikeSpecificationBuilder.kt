package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyLikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyLike
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class ReplyLikeSpecificationBuilder : SpecificationBuilder<ReplyLike, ReplyLikeDatabaseModel>() {

    override val filterPredicates = filterPredicates<ReplyLikeDatabaseModel> {
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
        field("replyId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("replyId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("replyId").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("replyId").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("replyId"))
            }
        }
    }
}
