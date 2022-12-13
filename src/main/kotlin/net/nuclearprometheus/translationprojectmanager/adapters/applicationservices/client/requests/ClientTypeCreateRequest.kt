package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests

data class ClientTypeCreateRequest(
    val name: String,
    val description: String,
    val corporate: Boolean
)
