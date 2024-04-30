package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class ThreadSpecificationFactory : SpecificationFactory<Thread, ThreadDatabaseModel>() {

    override val filterPredicates = filterPredicates<Thread, ThreadDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("title") { root, _, _ -> root.get("title") }
        comparable("createdAt") { root, _, _ -> root.get<ZonedDateTime>("createdAt") }
        enum("status") { root, _, _ -> root.get<ThreadStatusDatabaseModel>("status") }
        uniqueValue("authorId") { root, _, _ -> root.get<UUID>("authorId") }
        uniqueValue("projectId") { root, _, _ -> root.get<UUID>("projectId") }
        // TODO figure out subqueries
//        collection("tags") { root, query, builder ->
//            val subquery = query.subquery(String::class.java)
//            val subRoot = subquery.from(TagDatabaseModel::class.java)
//            subquery.select(subRoot.get("name"))
//                .where(builder.equal(subRoot.get<UUID>("threadId"), root.get<UUID>("id")))
//        }
    }
}