package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.mappers;

import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models.CountryExternalModel
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Country
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.LanguageCode

fun CountryExternalModel.toDomain() = Country(
    code = CountryCode(code),
    name = name,
    nativeNames = nativeNames,
    currencies = currencies.map { CurrencyCode(it.key) to it.value }.toMap(),
    languages = languages.map { LanguageCode(it.key) to it.value }.toMap(),
    emoji = emoji
)
