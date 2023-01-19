package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients.CountryClient
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import org.springframework.stereotype.Repository

@Repository
class CountryClientAdapter(private val client: CountryClient) : CountryRepository {

    override fun getAll() = client.getAll().map { it.toDomain() }
    override fun getByCode(code: String) = client.getByCode(code)?.toDomain()
    override fun getByNameLike(name: String) = client.getByNameLike(name).map { it.toDomain() }
}