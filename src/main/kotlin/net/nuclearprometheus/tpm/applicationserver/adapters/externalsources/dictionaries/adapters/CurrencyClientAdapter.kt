package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients.CurrencyClient
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors.CurrencyQueryExecutor
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class CurrencyClientAdapter(
    private val client: CurrencyClient,
    private val currencyQueryExecutor: CurrencyQueryExecutor
) : CurrencyRepository {

    override fun getAll() = client.getSymbols()
        .currencies
        .map { Currency(CurrencyCode(it.key), it.value) }

    override fun get(query: Query<Currency>) = currencyQueryExecutor.execute(query) { getAll() }

    override fun get(code: CurrencyCode) = client.getSymbols()
        .currencies
        .filterKeys { it == code.value.uppercase() }
        .map { Currency(CurrencyCode(it.key), it.value) }
        .firstOrNull()

    override fun getExchangeRates(code: CurrencyCode, amount: BigDecimal) = client.getLatest(code.value).toDomain(amount)
}
