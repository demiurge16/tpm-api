package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CurrencyExchangeRatesView
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyExchangeRates

fun CurrencyExchangeRates.toView() = CurrencyExchangeRatesView(
    baseCurrencyCode = baseCurrencyCode.value,
    rates = rates.map { it.key.value to it.value }.toMap()
)