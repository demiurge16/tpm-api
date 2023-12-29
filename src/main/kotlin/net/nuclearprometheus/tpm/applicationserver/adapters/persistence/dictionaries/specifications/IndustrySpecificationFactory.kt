package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.IndustryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import org.springframework.stereotype.Component
import java.util.*

@Component
class IndustrySpecificationFactory : SpecificationFactory<Industry, IndustryDatabaseModel>() {

    override val filterPredicates = filterPredicates<Industry, IndustryDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        boolean("active") { root, _, _ -> root.get("active") }
    }
}