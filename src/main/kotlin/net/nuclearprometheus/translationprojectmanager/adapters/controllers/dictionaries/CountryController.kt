package net.nuclearprometheus.translationprojectmanager.adapters.controllers.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.CountryApplicationService
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests.CountryListQuery
import net.nuclearprometheus.translationprojectmanager.utils.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/country")
class CountryController(private val service: CountryApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getCountries(query: CountryListQuery) =
        with(logger) {
            info("Countries controller, method getCountries")
            service.getCountries(query)
        }

    @GetMapping("/{code}")
    fun getCountryByCode(@PathVariable(name = "code") code: String) = service.getCountry(code)

    @GetMapping("/name/{name}")
    fun getCountriesByNameLike(@PathVariable(name = "name") name: String) = service.getCountriesByNameLike(name)

}
