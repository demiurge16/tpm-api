package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients.CurrencyClient
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class CurrencyClientAdapter(private val client: CurrencyClient) : CurrencyRepository {
    override fun getAll() = client.getSymbols()
        .symbols
        .map { Currency(CurrencyCode(it.key), it.value.description) }

    override fun get(code: CurrencyCode) = client.getSymbols()
        .symbols
        .filterKeys { it == code.value.uppercase() }
        .map { Currency(CurrencyCode(it.key), it.value.description) }
        .firstOrNull()

    override fun getExchangeRates(code: CurrencyCode, amount: BigDecimal) = client.getLatest(code.value, amount).toDomain(amount)
}
