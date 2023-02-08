package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.CountryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CountryListRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/country")
class CountryController(private val service: CountryApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getCountries(query: CountryListRequest) =
        with(logger) {
            info("Countries controller, method getCountries")
            service.getCountries(query)
        }

    @GetMapping("/{code}")
    fun getCountryByCode(@PathVariable(name = "code") code: String) =
        with(logger) {
            info("Countries controller, method getCountryByCode")
            service.getCountry(code)
        }

    @GetMapping("/name/{name}")
    fun getCountriesByNameLike(@PathVariable(name = "name") name: String) =
        with(logger) {
            info("Countries controller, method getCountriesByNameLike")
            service.getCountriesByNameLike(name)
        }

}
