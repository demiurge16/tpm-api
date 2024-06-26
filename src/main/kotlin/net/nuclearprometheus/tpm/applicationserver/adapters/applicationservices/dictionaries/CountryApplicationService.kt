package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.CountryMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.stereotype.Service

@Service
class CountryApplicationService(
    private val repository: CountryRepository,
    private val specificationBuilder: SpecificationBuilder<Country>
) {

    private val logger = loggerFor(this::class.java)

    fun getCountries(query: FilteredRequest<Country>) =
        with(logger) {
            info("getCountries($query)")
            repository.get(query.toQuery(specificationBuilder)).map { it.toView() }
        }

    fun getCountry(code: String) =
        with(logger) {
            info("getCountry($code)")
            repository.getByCode(code)?.toView() ?: throw NotFoundException("Country with code $code not found")
        }

    fun getCountriesByNameLike(name: String) =
        with(logger) {
            info("getCountriesByNameLike($name)")
            repository.getByNameLike(name).map { it.toView() }
        }
}
