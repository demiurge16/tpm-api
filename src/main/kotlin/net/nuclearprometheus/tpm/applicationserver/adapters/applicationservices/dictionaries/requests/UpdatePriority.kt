package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

data class UpdatePriority(
    val name: String,
    val description: String,
    val emoji: String,
    val value: Int
)
