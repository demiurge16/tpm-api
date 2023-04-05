package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.PriorityDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PriorityJpaRepository : JpaRepository<PriorityDatabaseModel, UUID>
