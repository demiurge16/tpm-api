package net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries

import java.math.BigDecimal

data class CurrencyExchangeRates(val baseCurrencyCode: CurrencyCode, val rates: Map<CurrencyCode, BigDecimal>)
