package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import java.math.BigDecimal

data class CurrencyExchangeRates(val baseCurrencyCode: CurrencyCode, val amount: BigDecimal, val rates: Map<CurrencyCode, BigDecimal>)
