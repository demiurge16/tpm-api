package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

sealed class ServiceTypeRequest {
    data class Create(val name: String, val description: String) : ServiceTypeRequest()
    data class Update(val name: String, val description: String) : ServiceTypeRequest()
}