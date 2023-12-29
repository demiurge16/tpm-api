package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import org.springframework.stereotype.Component
import java.util.*

@Component
class ClientTypeSpecificationFactory : SpecificationFactory<ClientType, ClientTypeDatabaseModel>() {

    override val filterPredicates = filterPredicates<ClientType, ClientTypeDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        boolean("corporate") { root, _, _ -> root.get("corporate") }
        boolean("active") { root, _, _ -> root.get("active") }
    }
}