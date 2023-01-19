package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CountryExternalModel

class CountryExternalModelDeserializer : JsonDeserializer<CountryExternalModel>() {

    private class Root(
        val name: Name,
        val cca3: String,
        val currencies: Map<String, Currency>,
        val capital: List<String>,
        val altSpellings: List<String>,
        val languages: Map<String, String>,
        val flag: String,
    )

    private class Name(val common: String, val official: String, val nativeName: Map<String, NativeName>)
    private class NativeName(val common: String, val official: String)
    private class Currency(val name: String, val symbol: String)

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): CountryExternalModel {
        val root = p?.readValueAs(Root::class.java) ?: throw IllegalStateException("Could not deserialize CountryExternalModel")

        val name = root.name.common
        val code = root.cca3
        val nativeNames = root.name.nativeName.values.map { it.common }
        val currencies = root.currencies.mapValues { it.value.name }
        val languages = root.languages.mapValues { it.value }
        val flag = root.flag

        return CountryExternalModel(code, name, nativeNames, currencies, languages, flag)
    }
}