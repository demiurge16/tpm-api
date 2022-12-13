package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests

data class ClientTypeUpdateRequest(
    val name: String,
    val description: String,
    val corporate: Boolean
)
