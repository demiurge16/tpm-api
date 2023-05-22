package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.CountryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CountryRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/v1/country")
class CountryController(private val service: CountryApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getCountries(query: CountryRequest.List) =
        with(logger) {
            info("GET /api/v1/country")

            ResponseEntity.ok().body(service.getCountries(query))
        }

    @GetMapping("/{code}")
    fun getCountryByCode(@PathVariable(name = "code") code: String) =
        with(logger) {
            info("GET /api/v1/country/$code")

            ResponseEntity.ok().body(service.getCountry(code))
        }

    @GetMapping("/name/{name}")
    fun getCountriesByNameLike(@PathVariable(name = "name") name: String) =
        with(logger) {
            info("GET /api/v1/country/name/$name")

            ResponseEntity.ok().body(service.getCountriesByNameLike(name))
        }
}
