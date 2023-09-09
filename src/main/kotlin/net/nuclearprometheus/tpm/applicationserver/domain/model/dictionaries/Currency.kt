package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity

open class Currency(id: CurrencyCode, val name: String) : Entity<CurrencyCode>(id)

class UnknownCurrency(id: CurrencyCode) : Currency(id, "Unknown")
