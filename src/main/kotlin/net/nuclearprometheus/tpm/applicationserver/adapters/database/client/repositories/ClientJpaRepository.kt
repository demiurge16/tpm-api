package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities.ClientDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClientJpaRepository : JpaRepository<ClientDatabaseModel, UUID>
