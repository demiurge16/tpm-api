package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrencySymbolsExternalModel(val success: Boolean, val currencies: Map<String, String>)
