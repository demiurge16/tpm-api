package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

data class CountryView(
    val code: String,
    val name: String,
    val nativeNames: List<String>,
    val currencies: Map<String, String>,
    val languages: Map<String, String>,
    val emoji: String,
)
