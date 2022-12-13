package net.nuclearprometheus.translationprojectmanager.adapters.controllers.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.CurrencyApplicationService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/currency")
class CurrencyController(private val service: CurrencyApplicationService) {

    @GetMapping("")
    fun getAll() = service.getAll()

    @GetMapping("/{code}")
    fun getByCode(@PathVariable(name = "code") code: String) = service.getByCode(code)

    @GetMapping("/{code}/exchange/{amount}")
    fun getExchangeRates(@PathVariable(name = "code") code: String, @PathVariable(name = "amount") amount: BigDecimal) = service.getExchangeRates(code, amount)
}
