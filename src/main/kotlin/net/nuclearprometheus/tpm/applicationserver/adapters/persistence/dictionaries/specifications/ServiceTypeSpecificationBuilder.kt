package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ServiceTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType
import org.springframework.stereotype.Component
import java.util.*

@Component
class ServiceTypeSpecificationBuilder : SpecificationBuilder<ServiceType, ServiceTypeDatabaseModel>() {

    override val filterPredicates = filterPredicates<ServiceType, ServiceTypeDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        boolean("active") { root, _, _ -> root.get("active") }
    }
}
