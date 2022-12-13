package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CountryView
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Country

fun Country.toView() = CountryView(
    code = code.value,
    name = name,
    nativeNames = nativeNames,
    currencies = currencies.map { it.key.value to it.value }.toMap(),
    languages = languages.map { it.key.value to it.value }.toMap(),
    emoji = emoji
)
