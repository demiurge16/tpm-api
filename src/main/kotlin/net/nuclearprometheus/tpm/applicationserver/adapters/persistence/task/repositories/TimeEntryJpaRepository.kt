package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeEntryDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface TimeEntryJpaRepository : JpaRepository<TimeEntryDatabaseModel, UUID>, JpaSpecificationExecutor<TimeEntryDatabaseModel>
