package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CountryView

interface CountryApplicationService {

    fun getCountries(): List<CountryView>
    fun getCountry(code: String): CountryView
    fun getCountriesByNameLike(name: String): List<CountryView>
}