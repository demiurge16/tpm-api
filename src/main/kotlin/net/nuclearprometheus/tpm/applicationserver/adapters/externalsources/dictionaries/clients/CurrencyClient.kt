package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CurrencyExchangeRatesExternalModel
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CurrencySymbolsExternalModel
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.math.BigDecimal

@FeignClient(name = "currencies", url = "http://api.exchangerate.host")
interface CurrencyClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/live?source={base}&access_key=10fa6bb928862f8f4800b150be047ea9"])
    @Cacheable(value = ["currencies-client-cache"], key = "'live'")
    fun getLatest(@PathVariable(name = "source") source: String): CurrencyExchangeRatesExternalModel

    @RequestMapping(method = [RequestMethod.GET], value = ["/list?access_key=10fa6bb928862f8f4800b150be047ea9"])
    @Cacheable(value = ["currencies-client-cache"], key = "'symbols'")
    fun getSymbols(): CurrencySymbolsExternalModel
}
