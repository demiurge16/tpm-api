package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class ReplySpecificationBuilder : SpecificationBuilder<Reply, ReplyDatabaseModel>() {

    override val filterPredicates = filterPredicates<Reply, ReplyDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        comparable("createdAt") { root, _, _ -> root.get<ZonedDateTime>("createdAt") }
        boolean("deleted") { root, _, _ -> root.get("deleted") }
        uniqueValue("authorId") { root, _, _ -> root.get<UUID>("authorId") }
        uniqueValue("parentReplyId") { root, _, _ -> root.get<UUID>("parentReplyId") }
        uniqueValue("threadId") { root, _, _ -> root.get<UUID>("threadId") }
    }
}
