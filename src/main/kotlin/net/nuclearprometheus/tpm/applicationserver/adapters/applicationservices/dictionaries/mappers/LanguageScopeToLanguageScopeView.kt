package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageScopeView
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope

fun LanguageScope.toView() = LanguageScopeView(
    code = this,
    name = title
)