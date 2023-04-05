package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TaskJpaRepository : JpaRepository<TaskDatabaseModel, UUID> {

    fun findAllByProjectId(projectId: UUID): List<TaskDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
}
