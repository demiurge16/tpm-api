package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import org.springframework.stereotype.Component
import java.util.*

@Component
class ClientSpecificationFactory : SpecificationFactory<Client, ClientDatabaseModel>() {

    override val filterPredicates = filterPredicates<Client, ClientDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        string("email") { root, _, _ -> root.get("email") }
        string("phone") { root, _, _ -> root.get("phone") }
        string("address") { root, _, _ -> root.get("address") }
        string("city") { root, _, _ -> root.get("city") }
        string("state") { root, _, _ -> root.get("state") }
        string("zip") { root, _, _ -> root.get("zip") }
        uniqueValue("countryCode") { root, _, _ -> root.get<String>("countryCode") }
        string("vat") { root, _, _ -> root.get("vat") }
        boolean("active") { root, _, _ -> root.get("active") }
        uniqueValue("clientTypeId") { root, _, _ -> root.join<ClientDatabaseModel, ClientTypeDatabaseModel>("type").get<UUID>("id") }
    }
}
