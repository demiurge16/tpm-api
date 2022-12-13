package net.nuclearprometheus.translationprojectmanager.adapters.database.client.adapters

import net.nuclearprometheus.translationprojectmanager.adapters.database.client.mappers.toDatabaseModel
import net.nuclearprometheus.translationprojectmanager.adapters.database.client.mappers.toDomain
import net.nuclearprometheus.translationprojectmanager.adapters.database.client.repositories.ClientTypeJpaRepository
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientType
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.client.ClientTypeRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ClientTypeRepositoryImpl(private var jpaRepository: ClientTypeJpaRepository) : ClientTypeRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: ClientTypeId): ClientType? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun create(clientType: ClientType) = jpaRepository.save(clientType.toDatabaseModel()).toDomain()
    override fun update(clientType: ClientType) = jpaRepository.save(clientType.toDatabaseModel()).toDomain()
}
