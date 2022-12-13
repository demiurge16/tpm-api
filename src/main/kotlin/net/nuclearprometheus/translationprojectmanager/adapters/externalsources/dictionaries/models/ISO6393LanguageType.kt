package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.models

enum class ISO6393LanguageType(val code: String, val description: String) {
    Living("L", "Living"),
    Extinct("E", "Extinct"),
    Historical("H", "Historical"),
    Constructed("C", "Constructed"),
    Ancient("A", "Ancient"),
    Special("S", "Special"),
}
