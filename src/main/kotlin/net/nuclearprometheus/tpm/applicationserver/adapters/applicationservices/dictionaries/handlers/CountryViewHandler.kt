package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.applyQuery
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CountryViewHandler(private val repository: CountryRepository) {

    private val logger = loggerFor(this::class.java)

    @Cacheable("countries-cache")
    fun getCountries(query: FilteredRequest<Country>) =
        with(logger) {
            info("Country view handler, no cache, method getCountries")
            info("Query: $query")

            repository.getAll().applyQuery(query).map { it.toView() }
        }

    @Cacheable("countries-cache")
    fun getCountry(code: String) =
        with(logger) {
            info("Country view handler, no cache, method getCountry")
            info("Code: $code")

            repository.getByCode(code)?.toView() ?: throw NotFoundException("Country with code $code not found")
        }

    @Cacheable("countries-cache")
    fun getCountriesByNameLike(name: String) =
        with(logger) {
            info("Country view handler, no cache, method getCountriesByNameLike")
            info("Name: $name")

            repository.getByNameLike(name).map { it.toView() }
        }
}
