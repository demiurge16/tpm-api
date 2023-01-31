package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.math.BigDecimal

data class CurrencyExchangeRatesView(val baseCurrencyCode: String, val rates: Map<String, BigDecimal>)