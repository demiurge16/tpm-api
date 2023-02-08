package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CurrencyExchangeRatesView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CurrencyView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Page
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import java.math.BigDecimal

interface CurrencyViewHandler {
    fun getAll(query: FilteredRequest<Currency>): Page<CurrencyView>
    fun getByCode(code: String): CurrencyView
    fun getExchangeRates(code: String, amount: BigDecimal): CurrencyExchangeRatesView
}