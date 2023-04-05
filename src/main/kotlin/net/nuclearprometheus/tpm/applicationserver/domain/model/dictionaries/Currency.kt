package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity

open class Currency(code: CurrencyCode, val name: String) : Entity<CurrencyCode>(code)

class UnknownCurrency(code: CurrencyCode) : Currency(code, "Unknown")
