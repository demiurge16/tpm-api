package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CurrencyExchangeRatesView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CurrencyView
import java.math.BigDecimal

interface CurrencyViewHandler {
    fun getAll(): List<CurrencyView>
    fun getByCode(code: String): CurrencyView
    fun getExchangeRates(code: String, amount: BigDecimal): CurrencyExchangeRatesView
}