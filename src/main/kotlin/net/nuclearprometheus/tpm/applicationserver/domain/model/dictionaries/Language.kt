package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

open class Language(
    open val code: LanguageCode,
    val iso6392T: String?,
    val iso6392B: String?,
    val iso6391: String?,
    val scope: LanguageScope,
    val type: LanguageType,
    val name: String
)

data class UnknownLanguage(override val code: LanguageCode) : Language(
    code = code,
    iso6392T = null,
    iso6392B = null,
    iso6391 = null,
    scope = LanguageScope.INDIVIDUAL,
    type = LanguageType.LIVING,
    name = "Unknown"
)
