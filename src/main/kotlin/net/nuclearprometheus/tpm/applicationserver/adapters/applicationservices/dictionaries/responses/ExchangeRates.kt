package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.math.BigDecimal

data class ExchangeRates(val baseCurrencyCode: String, val amount: BigDecimal, val rates: Map<String, BigDecimal>)