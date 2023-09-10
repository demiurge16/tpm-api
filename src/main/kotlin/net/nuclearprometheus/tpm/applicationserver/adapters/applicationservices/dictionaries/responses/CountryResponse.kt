package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

sealed class CountryResponse {
    data class Country(
        val code: String,
        val cca2: String,
        val ccn3: String,
        val name: Name,
        val topLevelDomains: List<String>,
        val currencies: Map<String, Currency>,
        val internationalDirectDialing: InternationalDirectDialing,
        val capital: List<String>,
        val altSpellings: List<String>,
        val languages: List<Language>,
        val translations: Map<String, Translation>,
        val flag: String,
        val postalCode: PostalCodeInfo
    ) : CountryResponse() {

        data class Name(
            val common: String,
            val official: String,
            val nativeName: Map<String, NativeName>
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

        data class Language(
            val code: String,
            val name: String
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
}
