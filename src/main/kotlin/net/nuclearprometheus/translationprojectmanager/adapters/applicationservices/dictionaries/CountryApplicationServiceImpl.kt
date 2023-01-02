package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers.CountryViewHandler
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests.CountryListQuery
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CountryView
import org.springframework.stereotype.Service

@Service
class CountryApplicationServiceImpl(private val countryViewHandler: CountryViewHandler) : CountryApplicationService {

    override fun getCountries(query: CountryListQuery): List<CountryView> = countryViewHandler.getCountries(query)
    override fun getCountry(code: String): CountryView = countryViewHandler.getCountry(code)
    override fun getCountriesByNameLike(name: String) = countryViewHandler.getCountriesByNameLike(name)
}
