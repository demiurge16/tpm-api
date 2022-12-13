package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CurrencyView
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Currency

fun Currency.toView() = CurrencyView(
    code = code.value,
    name = name
)
