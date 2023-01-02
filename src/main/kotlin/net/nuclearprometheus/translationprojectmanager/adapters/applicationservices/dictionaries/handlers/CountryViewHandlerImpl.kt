package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.mappers.toView
import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.requests.CountryListQuery
import net.nuclearprometheus.translationprojectmanager.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries.CountryRepository
import org.springframework.stereotype.Service

@Service
class CountryViewHandlerImpl(private val repository: CountryRepository) : CountryViewHandler {

    override fun getCountries(query: CountryListQuery) = repository.getAll()
        .filter { query.searchQuery().evaluate(it) }
        .drop(query.offset())
        .take(query.limit())
        .map { it.toView() }

    override fun getCountry(code: String) = repository.getByCode(code)?.toView() ?: throw NotFoundException("Country with code $code not found")
    override fun getCountriesByNameLike(name: String) = repository.getByNameLike(name).map { it.toView() }
}
