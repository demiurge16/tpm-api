package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers;

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CountryExternalModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode

fun CountryExternalModel.toDomain() = Country(
    code = CountryCode(code),
    name = name,
    nativeNames = nativeNames,
    currencies = currencies.map { CurrencyCode(it.key) to it.value }.toMap(),
    languages = languages.map { LanguageCode(it.key) to it.value }.toMap(),
    emoji = emoji
)
