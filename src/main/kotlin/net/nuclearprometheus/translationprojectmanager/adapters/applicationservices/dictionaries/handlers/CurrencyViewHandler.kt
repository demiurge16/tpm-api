package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CurrencyExchangeRatesView
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CurrencyView
import java.math.BigDecimal

interface CurrencyViewHandler {
    fun getAll(): List<CurrencyView>
    fun getByCode(code: String): CurrencyView
    fun getExchangeRates(code: String, amount: BigDecimal): CurrencyExchangeRatesView
}