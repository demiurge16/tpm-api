package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CurrencyViewHandlerImpl(private val repository: CurrencyRepository) : CurrencyViewHandler {

    override fun getAll() = repository.getAll().map { it.toView() }
    override fun getByCode(code: String) = repository.getByCode(CurrencyCode(code))?.toView() ?: throw NotFoundException("Currency with code $code not found")
    override fun getExchangeRates(code: String, amount: BigDecimal) = repository.getExchangeRates(CurrencyCode(code), amount).toView()
}
