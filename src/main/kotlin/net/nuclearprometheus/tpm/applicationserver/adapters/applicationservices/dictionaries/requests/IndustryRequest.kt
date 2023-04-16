package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

sealed class IndustryRequest {

    data class Create(
        val name: String,
        val description: String,
    ) : IndustryRequest()

    data class Update(
        val name: String,
        val description: String,
    ) : IndustryRequest()
}