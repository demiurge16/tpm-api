package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CurrencyExchangeRatesView
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CurrencyView
import java.math.BigDecimal

interface CurrencyApplicationService {
    fun getAll(): List<CurrencyView>
    fun getByCode(code: String): CurrencyView
    fun getExchangeRates(code: String, amount: BigDecimal): CurrencyExchangeRatesView
}
