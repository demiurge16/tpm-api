package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.CurrencyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.applyQuery
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CurrencyApplicationService(private val repository: CurrencyRepository) {

    private val logger = loggerFor(this::class.java)

    @Cacheable("currencies-cache")
    fun getCurrencies(query: FilteredRequest<Currency>) =
        with(logger) {
            info("getCurrencies($query)")

            repository.getAll().applyQuery(query).map { it.toView() }
        }

    @Cacheable("currencies-cache")
    fun getCurrencyByCode(code: String) =
        with(logger) {
            info("getCurrencyByCode($code)")

            repository.get(CurrencyCode(code))?.toView() ?: throw NotFoundException("Currency with code $code not found")
        }

    @Cacheable("currencies-cache")
    fun getExchangeRates(code: String, amount: BigDecimal) =
        with(logger) {
            info("getExchangeRates($code, $amount)")

            repository.getExchangeRates(CurrencyCode(code), amount).toView()
        }
}
