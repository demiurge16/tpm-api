package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

open class Currency(open val code: CurrencyCode, val name: String)

data class UnknownCurrency(override val code: CurrencyCode) : Currency(code, "Unknown")
