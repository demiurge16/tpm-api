package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.CurrencyViewHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CurrencyApplicationService(private val currencyViewHandler: CurrencyViewHandler) {

    private val logger = loggerFor(this::class.java)

    fun getAll(query: FilteredRequest<Currency>) =
        with(logger) {
            info("Currency application service, method getAll")
            currencyViewHandler.getAll(query)
        }

    fun getByCode(code: String) =
        with(logger) {
            info("Currency application service, method getByCode")
            currencyViewHandler.getByCode(code)
        }

    fun getExchangeRates(code: String, amount: BigDecimal) =
        with(logger) {
            info("Currency application service, method getExchangeRates")
            currencyViewHandler.getExchangeRates(code, amount)
        }

}