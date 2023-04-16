package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

sealed class PriorityRequest {

    data class Create(
        val name: String,
        val description: String,
        val emoji: String,
        val value: Int
    ) : PriorityRequest()

    data class Update(
        val name: String,
        val description: String,
        val emoji: String,
        val value: Int
    ) : PriorityRequest()
}