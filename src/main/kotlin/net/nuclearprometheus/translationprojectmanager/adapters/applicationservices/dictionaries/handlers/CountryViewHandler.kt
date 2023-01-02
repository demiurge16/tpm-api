package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests.CountryListQuery
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CountryView

interface CountryViewHandler {
    fun getCountries(query: CountryListQuery): List<CountryView>
    fun getCountry(code: String): CountryView
    fun getCountriesByNameLike(name: String): List<CountryView>
}