package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity

open class Country(
    cca3: CountryCode,
    val cca2: String,
    val ccn3: String,
    val name: Name,
    val topLevelDomains: List<String>,
    val currencies: Map<CurrencyCode, Currency>,
    val internationalDirectDialing: InternationalDirectDialing,
    val capital: List<String>,
    val altSpellings: List<String>,
    val languages: List<Language>,
    val translations: Map<LanguageCode, Translation>,
    val flag: String,
    val postalCode: PostalCodeInfo
) : Entity<CountryCode>(cca3) {

    data class Name(
        val common: String,
        val official: String,
        val nativeName: Map<LanguageCode, NativeName>
    ) {
        data class NativeName(
            val official: String,
            val common: String
        )
    }

    data class Currency(
        val name: String,
        val symbol: String
    )

    data class InternationalDirectDialing(
        val root: String,
        val suffixes: List<String>
    )

    data class Translation(
        val official: String,
        val common: String
    )

    data class PostalCodeInfo(
        val format: String,
        val regex: String
    )
}

class UnknownCountry(code: CountryCode) : Country(
    code,"xxx",
    "xxx",
    Name("Unknown", "Unknown", mapOf()),
    listOf(),
    mapOf(),
    InternationalDirectDialing("", listOf()),
    listOf(),
    listOf(),
    listOf(),
    mapOf(),
    "❌",
    PostalCodeInfo("", "")
)
