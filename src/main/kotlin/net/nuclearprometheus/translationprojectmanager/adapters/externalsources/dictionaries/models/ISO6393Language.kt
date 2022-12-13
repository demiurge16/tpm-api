package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models

data class ISO6393Language(
    val id: String,
    val part2B: String?,
    val part2T: String?,
    val part1: String?,
    val scope: ISO6393LanguageScope,
    val languageType: ISO6393LanguageType,
    val referenceName: String,
    val comment: String?,
)
