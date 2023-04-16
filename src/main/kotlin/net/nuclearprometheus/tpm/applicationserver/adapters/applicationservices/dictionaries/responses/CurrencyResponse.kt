package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.math.BigDecimal

sealed class CurrencyResponse {

    data class View(val code: String, val name: String) : CurrencyResponse()
    data class ExchangeRatesView(val baseCurrencyCode: String, val rates: Map<String, BigDecimal>) : CurrencyResponse()
}
