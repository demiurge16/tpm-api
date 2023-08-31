package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients.CountryClient
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors.CountryQueryExecutor
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import org.springframework.stereotype.Repository

@Repository
class CountryClientAdapter(
    private val client: CountryClient,
    private val countryQueryExecutor: CountryQueryExecutor
) : CountryRepository {

    override fun getAll() = client.getAll().map { it.toDomain() }
    override fun get(query: Query<Country>) = countryQueryExecutor.execute(query) { getAll() }
    override fun getByCode(code: String) = client.getByCode(code)?.toDomain()
    override fun getByNameLike(name: String) = client.getByNameLike(name).map { it.toDomain() }
}
