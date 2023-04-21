package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement

sealed class UnitRequest {

    data class Create(val name: String, val description: String, val volume: Int, val measurement: Measurement) : UnitRequest()
    data class Update(val name: String, val description: String, val volume: Int, val measurement: Measurement) : UnitRequest()
}
