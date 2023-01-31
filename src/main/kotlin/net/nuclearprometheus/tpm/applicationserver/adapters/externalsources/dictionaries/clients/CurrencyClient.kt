package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CurrencyExchangeRatesExternalModel
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CurrencySymbolsExternalModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.math.BigDecimal

@FeignClient(name = "currencies", url = "https://api.exchangerate.host")
interface CurrencyClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/latest?base={base}&amount={amount}"])
    fun getLatest(@PathVariable(name = "base") base: String, @PathVariable(name = "amount") amount: BigDecimal): CurrencyExchangeRatesExternalModel

    @RequestMapping(method = [RequestMethod.GET], value = ["/symbols"])
    fun getSymbols(): CurrencySymbolsExternalModel
}
