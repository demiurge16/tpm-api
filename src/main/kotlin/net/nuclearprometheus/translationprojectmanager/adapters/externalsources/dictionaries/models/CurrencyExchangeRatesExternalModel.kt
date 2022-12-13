package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
class CurrencyExchangeRatesExternalModel(val base: String, val date: String, val rates: Map<String, BigDecimal>)
