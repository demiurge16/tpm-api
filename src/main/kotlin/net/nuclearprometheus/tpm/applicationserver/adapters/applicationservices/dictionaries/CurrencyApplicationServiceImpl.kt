package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.CurrencyViewHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CurrencyApplicationServiceImpl(private val currencyViewHandler: CurrencyViewHandler) : CurrencyApplicationService {

    private val logger = loggerFor(this::class.java)

    override fun getAll(query: FilteredRequest<Currency>) =
        with(logger) {
            info("Currency application service, method getAll")
            currencyViewHandler.getAll(query)
        }

    override fun getByCode(code: String) =
        with(logger) {
            info("Currency application service, method getByCode")
            currencyViewHandler.getByCode(code)
        }

    override fun getExchangeRates(code: String, amount: BigDecimal) =
        with(logger) {
            info("Currency application service, method getExchangeRates")
            currencyViewHandler.getExchangeRates(code, amount)
        }

}