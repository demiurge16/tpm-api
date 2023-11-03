package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CurrencyExchangeRatesExternalModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyExchangeRates
import java.math.BigDecimal

fun CurrencyExchangeRatesExternalModel.toDomain(amount: BigDecimal) = CurrencyExchangeRates(
    baseCurrencyCode = CurrencyCode(source),
    amount = amount,
    rates = quotes.map { CurrencyCode(it.key.removePrefix(source)) to it.value * amount }.toMap()
)
