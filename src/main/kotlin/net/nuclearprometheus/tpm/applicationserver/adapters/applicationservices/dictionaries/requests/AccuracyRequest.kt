package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

sealed class AccuracyRequest {
    data class Create(val name: String, val description: String) : AccuracyRequest()
    data class Update(val name: String, val description: String) : AccuracyRequest()
}