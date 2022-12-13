package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models

enum class ISO6393LanguageScope(val code: String, val description: String) {
    Individual("I", "Individual language"),
    Macrolanguage("M", "Macrolanguage"),
    Special("S", "Special"),
}
