package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

data class CountryExternalModel(
    val name: Name,
    val tld: List<String>,
    val cca2: String,
    val ccn3: String,
    val cca3: String,
    val currencies: Map<String, Currency>,
    val idd: Idd,
    val capital: List<String>,
    val altSpellings: List<String>,
    val languages: Map<String, String>,
    val translations: Map<String, Translation>,
    val flag: String,
    val postalCode: PostalCodeInfo = PostalCodeInfo("", "")
) {
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

    data class Idd(
        val root: String,
        val suffixes: List<String>
    )

    data class Translation(
        val official: String,
        val common: String
    )

    data class PostalCodeInfo(
        val format: String = "",
        val regex: String = ""
    )
}
