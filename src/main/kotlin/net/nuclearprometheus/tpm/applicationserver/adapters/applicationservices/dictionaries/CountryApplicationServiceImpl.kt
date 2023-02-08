package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.CountryViewHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CountryView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service

@Service
class CountryApplicationServiceImpl(private val countryViewHandler: CountryViewHandler) : CountryApplicationService {

    private val logger = loggerFor(this::class.java)

    override fun getCountries(query: FilteredRequest<Country>) =
        with(logger) {
            info("Country application service, method getCountries")
            countryViewHandler.getCountries(query)
        }
    override fun getCountry(code: String): CountryView =
        with(logger) {
            info("Country application service, method getCountry")
            countryViewHandler.getCountry(code)
        }

    override fun getCountriesByNameLike(name: String) =
        with(logger) {
            info("Country application service, method getCountriesByNameLike")
            countryViewHandler.getCountriesByNameLike(name)
        }
}
