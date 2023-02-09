package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope

data class LanguageScopeView(
    val code: LanguageScope,
    val name: String
)
