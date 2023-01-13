package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.mappers.toView
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests.CountryListQuery
import net.nuclearprometheus.translationprojectmanager.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.translationprojectmanager.utils.logging.loggerFor
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CountryViewHandlerImpl(private val repository: CountryRepository) : CountryViewHandler {

    private val logger = loggerFor(this::class.java)

    @Cacheable("countries-cache")
    override fun getCountries(query: CountryListQuery) =
        with(logger) {
            info("Country view handler, no cache, method getCountries")
            info("Query: $query")

            repository.getAll()
                .filter { query.searchQuery().evaluate(it) }
                .sortedWith(query.sortComparator())
                .drop(query.offset())
                .take(query.limit())
                .map { it.toView() }
        }

    override fun getCountry(code: String) = repository.getByCode(code)?.toView() ?: throw NotFoundException("Country with code $code not found")
    override fun getCountriesByNameLike(name: String) = repository.getByNameLike(name).map { it.toView() }
}
