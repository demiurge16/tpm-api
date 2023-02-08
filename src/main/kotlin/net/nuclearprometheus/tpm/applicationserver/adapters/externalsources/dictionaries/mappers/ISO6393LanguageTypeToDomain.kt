package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393LanguageType
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType

fun ISO6393LanguageType.toDomain() = when (this) {
    ISO6393LanguageType.Living -> LanguageType.LIVING
    ISO6393LanguageType.Extinct -> LanguageType.EXTINCT
    ISO6393LanguageType.Historical -> LanguageType.HISTORICAL
    ISO6393LanguageType.Constructed -> LanguageType.CONSTRUCTED
    ISO6393LanguageType.Ancient -> LanguageType.ANCIENT
    ISO6393LanguageType.Special -> LanguageType.SPECIAL
}
