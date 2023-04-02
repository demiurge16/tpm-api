package net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.PriorityDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PriorityJpaRepository : JpaRepository<PriorityDatabaseModel, UUID>
