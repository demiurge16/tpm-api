package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.CurrencyApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CurrencyRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

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
