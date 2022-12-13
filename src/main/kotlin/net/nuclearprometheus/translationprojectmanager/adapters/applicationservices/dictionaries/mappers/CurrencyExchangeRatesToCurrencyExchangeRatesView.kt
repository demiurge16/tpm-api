package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CurrencyExchangeRatesView
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CurrencyExchangeRates

fun CurrencyExchangeRates.toView() = CurrencyExchangeRatesView(
    baseCurrencyCode = baseCurrencyCode.value,
    rates = rates.map { it.key.value to it.value }.toMap()
)