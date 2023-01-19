package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageView
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language

fun Language.toView() = LanguageView(
    code = code.value,
    name = name
)
