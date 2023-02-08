package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.CurrencyApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.CurrencyListRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
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
    fun getAll(query: CurrencyListRequest) =
        with(logger) {
            info("Currency controller, method getAll")
            service.getAll(query)
        }

    @GetMapping("/{code}")
    fun getByCode(@PathVariable(name = "code") code: String) =
        with(logger) {
            info("Currency controller, method getByCode")
            service.getByCode(code)
        }

    @GetMapping("/{code}/exchange/{amount}")
    fun getExchangeRates(@PathVariable(name = "code") code: String, @PathVariable(name = "amount") amount: BigDecimal) =
        with(logger) {
            info("Currency controller, method getExchangeRates")
            service.getExchangeRates(code, amount)
        }
}
