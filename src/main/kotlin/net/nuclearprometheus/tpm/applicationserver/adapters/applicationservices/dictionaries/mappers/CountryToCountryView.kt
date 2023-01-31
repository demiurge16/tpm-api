package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CountryView
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country

fun Country.toView() = CountryView(
    code = code.value,
    name = name,
    nativeNames = nativeNames,
    currencies = currencies.map { it.key.value to it.value }.toMap(),
    languages = languages.map { it.key.value to it.value }.toMap(),
    emoji = emoji
)
