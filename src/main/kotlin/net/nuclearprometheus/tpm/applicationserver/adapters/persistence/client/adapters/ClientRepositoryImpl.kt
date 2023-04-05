package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.adapters.ClientTypeRepositoryImpl.Mapping.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.adapters.ClientTypeRepositoryImpl.Mapping.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.repositories.ClientJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnknownCountry
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CountryRepository
import org.springframework.stereotype.Repository

@Repository
class ClientRepositoryImpl(
    private val jpaRepository: ClientJpaRepository,
    private val countryRepository: CountryRepository
) : ClientRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(countryRepository) }
    override fun get(id: ClientId): Client? = jpaRepository.findById(id.value).map { it.toDomain(countryRepository) }.orElse(null)
    override fun get(ids: List<ClientId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain(countryRepository) }
    override fun create(entity: Client) = jpaRepository.save(entity.toDatabaseModel()).toDomain(countryRepository)
    override fun createAll(entities: List<Client>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(countryRepository) }
    override fun update(entity: Client) = jpaRepository.save(entity.toDatabaseModel()).toDomain(countryRepository)
    override fun updateAll(entities: List<Client>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(countryRepository) }
    override fun delete(id: ClientId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ClientId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mapping {
        fun ClientDatabaseModel.toDomain(countryRepository: CountryRepository) = Client(
            id = ClientId(id),
            name = name,
            email = email,
            phone = phone,
            address = address,
            city = city,
            state = state,
            zip = zip,
            country = countryRepository.getByCode(countryCode) ?: UnknownCountry(CountryCode(countryCode)),
            vat = vat,
            notes = notes,
            type = type.toDomain(),
            active = active
        )

        fun Client.toDatabaseModel() = ClientDatabaseModel(
            id = id.value,
            name = name,
            email = email,
            phone = phone,
            address = address,
            city = city,
            state = state,
            zip = zip,
            countryCode = country.id.value,
            vat = vat,
            notes = notes,
            type = type.toDatabaseModel(),
            active = active
        )
    }
}
