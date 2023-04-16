package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientTypeDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClientTypeJpaRepository : JpaRepository<ClientTypeDatabaseModel, UUID>