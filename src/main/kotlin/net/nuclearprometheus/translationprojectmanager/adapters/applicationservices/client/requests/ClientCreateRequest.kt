package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.client.requests

import java.util.UUID

data class ClientCreateRequest(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val state: String,
    val zip: String,
    val countryCode: String,
    val vat: String,
    val notes: String,
    val clientTypeId: UUID
)
