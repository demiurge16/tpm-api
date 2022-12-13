package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.serialization.CountryExternalModelDeserializer

@JsonDeserialize(using = CountryExternalModelDeserializer::class)
data class CountryExternalModel(
    val code: String,
    val name: String,
    val nativeNames: List<String>,
    val currencies: Map<String, String>,
    val languages: Map<String, String>,
    val emoji: String
)
