package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.CurrencyViewHandler
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CurrencyApplicationServiceImpl(private val currencyViewHandler: CurrencyViewHandler) : CurrencyApplicationService {

    override fun getAll() = currencyViewHandler.getAll()
    override fun getByCode(code: String) = currencyViewHandler.getByCode(code)
    override fun getExchangeRates(code: String, amount: BigDecimal) = currencyViewHandler.getExchangeRates(code, amount)
}