package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType

sealed class LanguageResponse {

    data class Language(
        val code: String,
        val iso6392T: String?,
        val iso6392B: String?,
        val iso6391: String?,
        val scope: LanguageScope,
        val type: LanguageType,
        val name: String
    )

    data class Scope(
        val code: LanguageScope,
        val name: String
    )

    data class Type(
        val code: LanguageType,
        val name: String
    )
}
