package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.clients.CountryClient
import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries.CountryRepository
import org.springframework.stereotype.Repository

@Repository
class CountryClientAdapter(private val client: CountryClient) : CountryRepository {

    override fun getAll() = client.getAll().map { it.toDomain() }
    override fun getByCode(code: String) = client.getByCode(code)?.toDomain()
    override fun getByNameLike(name: String) = client.getByNameLike(name).map { it.toDomain() }
}