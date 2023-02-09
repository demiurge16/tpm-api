package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType

data class LanguageTypeView(
    val code: LanguageType,
    val name: String
)