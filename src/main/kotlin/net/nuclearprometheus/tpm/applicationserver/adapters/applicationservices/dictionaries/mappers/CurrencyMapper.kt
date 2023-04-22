package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CurrencyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyExchangeRates

object CurrencyMapper {

    fun CurrencyExchangeRates.toView() = CurrencyResponse.ExchangeRatesView(
        baseCurrencyCode = baseCurrencyCode.value,
        amount = amount,
        rates = rates.map { it.key.value to it.value }.toMap()
    )

    fun Currency.toView() = CurrencyResponse.Currency(
        code = id.value,
        name = name
    )
}