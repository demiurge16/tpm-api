package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models

data class CurrencySymbolDescription(val code: String, val description: String)
data class CurrencySymbolsExternalModel(val success: Boolean, val symbols: Map<String, CurrencySymbolDescription>)
