package net.nuclearprometheus.translationprojectmanager.adapters.database.client.repositories

import net.nuclearprometheus.translationprojectmanager.adapters.database.client.entities.ClientDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClientJpaRepository : JpaRepository<ClientDatabaseModel, UUID> {
}
