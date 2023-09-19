package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.CurrencyApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CurrencyRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CurrencyResponse
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
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/currency")
class CurrencyController(private val service: CurrencyApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getAll(query: CurrencyRequest.List) =
        with(logger) {
            info("GET /api/v1/currency")

            ResponseEntity.ok().body(service.getCurrencies(query))
        }

    @GetMapping("/export", produces = ["text/csv"])
    fun exportCountries(query: CurrencyRequest.List) = with(logger) {
        info("GET /api/v1/currency/export")

        val currencies = service.getCurrencies(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<CurrencyResponse.Currency>(writer).build()
            beanToCsv.write(currencies.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "currencies-${LocalDate.now()}.csv"
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
    fun getByCode(@PathVariable(name = "code") code: String) =
        with(logger) {
            info("GET /api/v1/currency/$code")

            ResponseEntity.ok().body(service.getCurrencyByCode(code))
        }

    @GetMapping("/{code}/exchange/{amount}")
    fun getExchangeRates(@PathVariable(name = "code") code: String, @PathVariable(name = "amount") amount: BigDecimal) =
        with(logger) {
            info("GET /api/v1/currency/$code/exchange/$amount")

            ResponseEntity.ok().body(service.getExchangeRates(code, amount))
        }
}
