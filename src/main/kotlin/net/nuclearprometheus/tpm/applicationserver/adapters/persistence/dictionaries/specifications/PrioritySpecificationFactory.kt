package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.PriorityDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority
import org.springframework.stereotype.Component
import java.util.*

@Component
class PrioritySpecificationFactory : SpecificationFactory<Priority, PriorityDatabaseModel>() {

    override val filterPredicates = filterPredicates<Priority, PriorityDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        comparable("value") { root, _, _ -> root.get<Int>("value") }
        boolean("active") { root, _, _ -> root.get("active") }
    }
}