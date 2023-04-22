package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CurrencyExchangeRatesExternalModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyExchangeRates
import java.math.BigDecimal

fun CurrencyExchangeRatesExternalModel.toDomain(amount: BigDecimal) = CurrencyExchangeRates(
    baseCurrencyCode = CurrencyCode(base),
    amount = amount,
    rates = rates.map { CurrencyCode(it.key) to it.value }.toMap()
)
