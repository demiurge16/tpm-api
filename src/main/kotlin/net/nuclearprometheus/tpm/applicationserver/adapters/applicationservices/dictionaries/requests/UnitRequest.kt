package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

sealed class UnitRequest {

    data class Create(val name: String, val description: String) : UnitRequest()
    data class Update(val name: String, val description: String) : UnitRequest()
}