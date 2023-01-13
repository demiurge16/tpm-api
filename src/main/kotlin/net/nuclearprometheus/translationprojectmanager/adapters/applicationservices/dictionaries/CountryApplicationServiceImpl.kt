package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers.CountryViewHandler
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests.CountryListQuery
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.responses.CountryView
import net.nuclearprometheus.translationprojectmanager.utils.logging.loggerFor
import org.springframework.stereotype.Service

@Service
class CountryApplicationServiceImpl(private val countryViewHandler: CountryViewHandler) : CountryApplicationService {

    private val logger = loggerFor(this::class.java)

    override fun getCountries(query: CountryListQuery): List<CountryView> =
        with(logger) {
            info("Country application service, method getCountries")
            countryViewHandler.getCountries(query)
        }
    override fun getCountry(code: String): CountryView = countryViewHandler.getCountry(code)
    override fun getCountriesByNameLike(name: String) = countryViewHandler.getCountriesByNameLike(name)
}
