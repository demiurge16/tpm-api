package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests.CountryListQuery
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CountryView

interface CountryApplicationService {

    fun getCountries(query: CountryListQuery): List<CountryView>
    fun getCountry(code: String): CountryView
    fun getCountriesByNameLike(name: String): List<CountryView>
}