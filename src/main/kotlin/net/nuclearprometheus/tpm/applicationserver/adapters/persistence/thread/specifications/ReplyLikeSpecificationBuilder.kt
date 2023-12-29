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

    override val filterPredicates = filterPredicates<ReplyLike, ReplyLikeDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        comparable("createdAt") { root, _, _ -> root.get<ZonedDateTime>("createdAt") }
        uniqueValue("authorId") { root, _, _ -> root.get<UUID>("authorId") }
        uniqueValue("replyId") { root, _, _ -> root.get<UUID>("replyId") }
    }
}
