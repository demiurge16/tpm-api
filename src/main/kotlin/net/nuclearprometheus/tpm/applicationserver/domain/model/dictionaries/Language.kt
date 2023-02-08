package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

data class Language(
    val code: LanguageCode,
    val iso6392T: String?,
    val iso6392B: String?,
    val iso6391: String?,
    val scope: LanguageScope,
    val type: LanguageType,
    val name: String
)
