package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CountryView

interface CountryViewHandler {
    fun getCountries(): List<CountryView>
    fun getCountry(code: String): CountryView
    fun getCountriesByNameLike(name: String): List<CountryView>
}