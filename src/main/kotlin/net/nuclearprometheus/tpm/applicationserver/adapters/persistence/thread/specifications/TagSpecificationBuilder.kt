package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.TagDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Tag
import org.springframework.stereotype.Component
import java.util.*

@Component
class TagSpecificationBuilder : SpecificationBuilder<Tag, TagDatabaseModel>() {

    override val filterPredicates = filterPredicates<Tag, TagDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        uniqueValue("threadId") { root, _, _ -> root.get<UUID>("threadId") }
    }
}