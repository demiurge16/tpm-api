package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.serialization

import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models.ISO6393IndividualLanguageStatus

fun String.asISO6393IndividualLanguageStatus() = when (this) {
    "A" -> ISO6393IndividualLanguageStatus.Active
    "C" -> ISO6393IndividualLanguageStatus.Retired
    else -> throw IllegalArgumentException("Unknown ISO 639-3 individual language status: $this")
}
