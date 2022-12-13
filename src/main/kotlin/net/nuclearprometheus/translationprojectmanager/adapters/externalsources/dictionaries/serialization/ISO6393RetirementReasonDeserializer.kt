package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.serialization

import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models.ISO6393RetirementReason

fun String.asISO6393RetirementReason() = when (this) {
    "C" -> ISO6393RetirementReason.Change
    "D" -> ISO6393RetirementReason.Duplicate
    "N" -> ISO6393RetirementReason.Nonexistent
    "S" -> ISO6393RetirementReason.Split
    "M" -> ISO6393RetirementReason.Merge
    else -> throw IllegalArgumentException("Unknown ISO 639-3 retirement reason code: $this")
}
