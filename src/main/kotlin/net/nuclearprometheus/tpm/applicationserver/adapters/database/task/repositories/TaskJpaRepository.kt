package net.nuclearprometheus.tpm.applicationserver.adapters.database.task.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.task.entities.TaskDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TaskJpaRepository : JpaRepository<TaskDatabaseModel, UUID>
