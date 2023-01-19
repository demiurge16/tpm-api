package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

open class Country(open val code: CountryCode, val name: String, val nativeNames: List<String>, val currencies: Map<CurrencyCode, String>, val languages: Map<LanguageCode, String>, val emoji: String)

data class UnknownCountry(override val code: CountryCode) : Country(code, "Unknown", listOf("Unknown"), mapOf(CurrencyCode("xx") to "Unknown"), mapOf(LanguageCode("xx") to "Unknown"), "❌")
