package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface TaskJpaRepository : JpaRepository<TaskDatabaseModel, UUID>, JpaSpecificationExecutor<TaskDatabaseModel> {

    fun findAllByProjectId(projectId: UUID): List<TaskDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
}
