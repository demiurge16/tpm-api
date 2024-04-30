package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.MeasurementDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import org.springframework.stereotype.Component
import java.util.*

@Component
class UnitSpecificationFactory : SpecificationFactory<Unit, UnitDatabaseModel>() {

    override val filterPredicates = filterPredicates<Unit, UnitDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        comparable("volume") { root, _, _ -> root.get<Int>("volume") }
        enum("measurement") { root, _, _ -> root.get<MeasurementDatabaseModel>("measurement") }
        boolean("active") { root, _, _ -> root.get("active") }
    }
}
