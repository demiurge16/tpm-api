package net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries

import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Currency
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CurrencyExchangeRates
import java.math.BigDecimal

interface CurrencyRepository {
    fun getAll(): List<Currency>
    fun getByCode(code: CurrencyCode): Currency?
    fun getExchangeRates(code: CurrencyCode, amount: BigDecimal): CurrencyExchangeRates
}
