package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models.CurrencyExchangeRatesExternalModel
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.CurrencyExchangeRates

fun CurrencyExchangeRatesExternalModel.toDomain() = CurrencyExchangeRates(
    baseCurrencyCode = CurrencyCode(base),
    rates = rates.map { CurrencyCode(it.key) to it.value }.toMap()
)
