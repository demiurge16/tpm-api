package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Language

fun Map<String, String>.toISO6393Language() = ISO6393Language(
    id = this["Id"]!!,
    part2B = this["Part2B"],
    part2T = this["Part2T"],
    part1 = this["Part1"],
    scope = this["Scope"]!!.asISO6393LanguageScope(),
    languageType = this["Language_Type"]!!.asISO6393LanguageType(),
    referenceName = this["Ref_Name"]!!,
    comment = this["Comment"],
)




