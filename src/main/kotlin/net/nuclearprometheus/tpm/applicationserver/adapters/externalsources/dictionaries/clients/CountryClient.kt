package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CountryExternalModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "countries", url = "https://restcountries.com/v3.1")
interface CountryClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/all?fields=name,cca3,currencies,flag,languages"])
    fun getAll(): List<CountryExternalModel>

    @RequestMapping(method = [RequestMethod.GET], value = ["/alpha/{code}?fields=name,cca3,currencies,flag,languages"])
    fun getByCode(@PathVariable(name = "code", required = true) code: String): CountryExternalModel?

    @RequestMapping(method = [RequestMethod.GET], value = ["/name/{name}?fields=name,cca3,currencies,flag,languages"])
    fun getByNameLike(@PathVariable(name = "name", required = true) name: String): List<CountryExternalModel>
}
