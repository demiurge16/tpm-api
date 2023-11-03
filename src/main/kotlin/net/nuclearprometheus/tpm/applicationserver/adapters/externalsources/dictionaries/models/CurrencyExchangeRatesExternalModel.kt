package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
class CurrencyExchangeRatesExternalModel(val source: String, val quotes: Map<String, BigDecimal>)
