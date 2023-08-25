package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyExchangeRates
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import java.math.BigDecimal

interface CurrencyRepository {
    fun getAll(): List<Currency>
    fun get(query: Query<Currency>): Page<Currency>
    fun get(code: CurrencyCode): Currency?
    fun getExchangeRates(code: CurrencyCode, amount: BigDecimal): CurrencyExchangeRates
}
