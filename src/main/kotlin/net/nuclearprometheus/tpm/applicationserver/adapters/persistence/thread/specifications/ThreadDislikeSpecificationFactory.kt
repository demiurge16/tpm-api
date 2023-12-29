package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDislikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadDislike
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class ThreadDislikeSpecificationFactory : SpecificationFactory<ThreadDislike, ThreadDislikeDatabaseModel>() {

    override val filterPredicates = filterPredicates<ThreadDislike, ThreadDislikeDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        comparable("createdAt") { root, _, _ -> root.get<ZonedDateTime>("createdAt") }
        uniqueValue("authorId") { root, _, _ -> root.get<UUID>("authorId") }
        uniqueValue("threadId") { root, _, _ -> root.get<UUID>("threadId") }
    }
}
