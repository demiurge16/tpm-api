package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClientJpaRepository : JpaRepository<ClientDatabaseModel, UUID>