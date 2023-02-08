package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageView
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType

fun Language.toView() = LanguageView(
    code = code.value,
    iso6392T = iso6392T,
    iso6392B = iso6392B,
    iso6391 = iso6391,
    scope = scope,
    type = type,
    name = name
)
