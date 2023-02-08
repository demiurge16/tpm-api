package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CountryView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Page
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country

interface CountryViewHandler {
    fun getCountries(query: FilteredRequest<Country>): Page<CountryView>
    fun getCountry(code: String): CountryView
    fun getCountriesByNameLike(name: String): List<CountryView>
}