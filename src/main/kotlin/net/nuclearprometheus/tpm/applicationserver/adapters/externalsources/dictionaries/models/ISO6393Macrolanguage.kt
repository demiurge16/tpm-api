package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models

data class ISO6393Macrolanguage(
    val macrolanguageId: String,
    val individualLanguageId: String,
    val individualLanguageStatus: ISO6393IndividualLanguageStatus
)