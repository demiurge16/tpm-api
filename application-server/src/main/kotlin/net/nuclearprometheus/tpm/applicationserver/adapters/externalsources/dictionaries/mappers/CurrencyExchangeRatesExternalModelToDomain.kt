package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CurrencyExchangeRatesExternalModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyExchangeRates

fun CurrencyExchangeRatesExternalModel.toDomain() = CurrencyExchangeRates(
    baseCurrencyCode = CurrencyCode(base),
    rates = rates.map { CurrencyCode(it.key) to it.value }.toMap()
)
