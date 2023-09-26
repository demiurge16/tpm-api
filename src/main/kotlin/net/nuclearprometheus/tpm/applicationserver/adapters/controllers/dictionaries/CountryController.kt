package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.CountryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ListCountries
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Country as CountryResponse
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.time.LocalDate
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/country")
class CountryController(private val service: CountryApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getCountries(query: ListCountries) =
        with(logger) {
            info("GET /api/v1/country")

            ResponseEntity.ok().body(service.getCountries(query))
        }

    @GetMapping("/export", produces = ["text/csv"])
    fun exportCountries(query: ListCountries) = with(logger) {
        info("GET /api/v1/country/export")

        val countries = service.getCountries(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<CountryResponse>(writer).build()
            beanToCsv.write(countries.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "countries-${LocalDate.now()}.csv"
        val resource = InputStreamResource(input)

        ResponseEntity.ok()
            .headers(
                HttpHeaders().apply {
                    add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
                    add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    add(HttpHeaders.PRAGMA, "no-cache")
                    add(HttpHeaders.EXPIRES, "0")
                }
            )
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource)
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
