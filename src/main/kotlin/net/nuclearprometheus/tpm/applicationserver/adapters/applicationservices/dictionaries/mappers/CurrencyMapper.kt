package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ExchangeRates
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Currency as CurrencyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyExchangeRates

object CurrencyMapper {

    fun CurrencyExchangeRates.toView() = ExchangeRates(
        baseCurrencyCode = baseCurrencyCode.value,
        amount = amount,
        rates = rates.map { it.key.value to it.value }.toMap()
    )

    fun Currency.toView() = CurrencyResponse(
        code = id.value,
        name = name
    )
}