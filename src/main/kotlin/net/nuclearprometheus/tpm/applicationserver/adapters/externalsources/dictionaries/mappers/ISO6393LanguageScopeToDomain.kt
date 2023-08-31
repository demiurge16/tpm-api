package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope

fun ISO6393LanguageScope.toDomain() = when (this) {
    ISO6393LanguageScope.Individual -> LanguageScope.INDIVIDUAL
    ISO6393LanguageScope.Macrolanguage -> LanguageScope.MACROLANGUAGE
    ISO6393LanguageScope.Special -> LanguageScope.SPECIAL
}

