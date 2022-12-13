package net.nuclearprometheus.translationprojectmanager.adapters.database.client.repositories

import net.nuclearprometheus.translationprojectmanager.adapters.database.client.entities.ClientTypeDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClientTypeJpaRepository : JpaRepository<ClientTypeDatabaseModel, UUID> {

}
