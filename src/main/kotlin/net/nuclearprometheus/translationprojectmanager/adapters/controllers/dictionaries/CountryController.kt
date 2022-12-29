package net.nuclearprometheus.translationprojectmanager.adapters.controllers.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.CountryApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/country")
class CountryController(private val service: CountryApplicationService) {

    @GetMapping("")
    fun getCountries(@RequestParam("query") query: UUID) = service.getCountries()

    @GetMapping("/{code}")
    fun getCountryByCode(@PathVariable(name = "code") code: String) = service.getCountry(code)

    @GetMapping("/name/{name}")
    fun getCountriesByNameLike(@PathVariable(name = "name") name: String) = service.getCountriesByNameLike(name)

    @GetMapping("/query")
    fun getCountryQueries() = println("Test")

    @PostMapping("/query")
    fun createCountryQuery(@RequestBody query: Query) = println(query)
}

data class Query(
    val page: Int,
    val pageSize: Int,
    val sort: String,
    val direction: SortDirection,
    val filters: List<Filter>,
)

enum class SortDirection {
    ASC,
    DESC,
}

data class Filter(
    val field: String,
    val operator: FilterOperator,
    val value: String
)

enum class FilterOperator {
    EQUALS,
    NOT_EQUALS,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL_TO,
    LESS_THAN,
    LESS_THAN_OR_EQUAL_TO,
    IN,
    NOT_IN,
    LIKE,
    NOT_LIKE,
    IS_NULL,
    IS_NOT_NULL,
}
