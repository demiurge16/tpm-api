package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.LanguageView
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Language

fun Language.toView() = LanguageView(
    code = code.value,
    name = name
)
