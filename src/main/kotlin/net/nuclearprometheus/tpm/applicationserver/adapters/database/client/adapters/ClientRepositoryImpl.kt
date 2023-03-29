package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.repositories.ClientJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ClientRepositoryImpl(
    private val jpaRepository: ClientJpaRepository,
    private val countryRepository: CountryRepository
) : ClientRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(countryRepository) }
    override fun get(id: ClientId): Client? = jpaRepository.findById(id.value).map { it.toDomain(countryRepository) }.orElse(null)
    override fun get(ids: List<ClientId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain(countryRepository) }
    override fun create(client: Client) = jpaRepository.save(client.toDatabaseModel()).toDomain(countryRepository)
    override fun update(client: Client) = jpaRepository.save(client.toDatabaseModel()).toDomain(countryRepository)
    override fun delete(id: ClientId) = jpaRepository.deleteById(id.value)
}
