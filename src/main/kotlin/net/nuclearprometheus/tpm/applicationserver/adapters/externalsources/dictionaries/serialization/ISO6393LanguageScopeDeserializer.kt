package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393LanguageScope

fun String.asISO6393LanguageScope() = when (this) {
    "I" -> ISO6393LanguageScope.Individual
    "M" -> ISO6393LanguageScope.Macrolanguage
    "S" -> ISO6393LanguageScope.Special
    else -> throw IllegalArgumentException("Unknown ISO 639-3 language scope: $this")
}
