package net.nuclearprometheus.translationprojectmanager.adapters.controllers.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.CountryApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/country")
class CountryController(private val service: CountryApplicationService) {

    @GetMapping("")
    fun getCountries() = service.getCountries()

    @GetMapping("/{code}")
    fun getCountryByCode(@PathVariable(name = "code") code: String) = service.getCountry(code)

    @GetMapping("/name/{name}")
    fun getCountriesByNameLike(@PathVariable(name = "name") name: String) = service.getCountriesByNameLike(name)
}