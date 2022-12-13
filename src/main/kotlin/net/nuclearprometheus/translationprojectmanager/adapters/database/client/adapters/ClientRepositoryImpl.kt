package net.nuclearprometheus.translationprojectmanager.adapters.database.client.adapters

import net.nuclearprometheus.translationprojectmanager.adapters.database.client.mappers.toDatabaseModel
import net.nuclearprometheus.translationprojectmanager.adapters.database.client.mappers.toDomain
import net.nuclearprometheus.translationprojectmanager.adapters.database.client.repositories.ClientJpaRepository
import net.nuclearprometheus.translationprojectmanager.domain.model.client.Client
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries.CountryRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ClientRepositoryImpl(
    private val jpaRepository: ClientJpaRepository,
    private val countryRepository: CountryRepository
) : ClientRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(countryRepository) }
    override fun get(id: UUID): Client? = jpaRepository.findById(id).map { it.toDomain(countryRepository) }.orElse(null)
    override fun create(clientType: Client) = jpaRepository.save(clientType.toDatabaseModel()).toDomain(countryRepository)
    override fun update(clientType: Client) = jpaRepository.save(clientType.toDatabaseModel()).toDomain(countryRepository)
}
