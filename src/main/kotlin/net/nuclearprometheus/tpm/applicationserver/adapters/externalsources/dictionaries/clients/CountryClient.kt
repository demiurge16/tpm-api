package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CountryExternalModel
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "countries", url = "https://restcountries.com/v3.1")
interface CountryClient {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/all?fields=name,tld,cca2,ccn3,cca3,currencies,idd,capital,altSpellings,languages,translations,flag,postalCode"]
    )
    @Cacheable(value = ["countries-client-cache"], key = "'countries-all'")
    fun getAll(): List<CountryExternalModel>

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/alpha/{code}?fields=name,tld,cca2,ccn3,cca3,currencies,idd,capital,altSpellings,languages,translations,flag,postalCode"]
    )
    @Cacheable(value = ["countries-client-cache"], key = "'countries-alpha-' + #code")
    fun getByCode(@PathVariable(name = "code", required = true) code: String): CountryExternalModel?

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/name/{name}?fields=name,tld,cca2,ccn3,cca3,currencies,idd,capital,altSpellings,languages,translations,flag,postalCode"]
    )
    @Cacheable(value = ["countries-client-cache"], key = "'countries-name-' + #name")
    fun getByNameLike(@PathVariable(name = "name", required = true) name: String): List<CountryExternalModel>
}
