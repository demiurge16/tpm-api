package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities.ClientTypeDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClientTypeJpaRepository : JpaRepository<ClientTypeDatabaseModel, UUID> {

}
