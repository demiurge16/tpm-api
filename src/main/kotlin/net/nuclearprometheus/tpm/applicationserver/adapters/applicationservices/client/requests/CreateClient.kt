package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.*

data class CreateClient(
    @field:[NotBlank(message = "Name is required")] val name: String,
    @field:[NotBlank(message = "Email is required") Email] val email: String,
    @field:[NotBlank(message = "Phone is required")] val phone: String,
    @field:[NotBlank(message = "Address is required")] val address: String,
    @field:[NotBlank(message = "City is required")] val city: String,
    @field:[NotBlank(message = "State is required")] val state: String,
    @field:[NotBlank(message = "Zip is required")] val zip: String,
    @field:[NotBlank(message = "Country code is required")] val countryCode: String,
    val vat: String,
    val notes: String,
    val clientTypeId: UUID
)