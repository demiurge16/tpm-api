package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadLikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadLike
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class ThreadLikeSpecificationFactory : SpecificationFactory<ThreadLike, ThreadLikeDatabaseModel>() {

    override val filterPredicates = filterPredicates<ThreadLike, ThreadLikeDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        comparable("createdAt") { root, _, _ -> root.get<ZonedDateTime>("createdAt") }
        uniqueValue("authorId") { root, _, _ -> root.get<UUID>("authorId") }
        uniqueValue("threadId") { root, _, _ -> root.get<UUID>("threadId") }
    }
}
