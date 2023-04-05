package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CurrencyView
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency

fun Currency.toView() = CurrencyView(
    code = id.value,
    name = name
)
