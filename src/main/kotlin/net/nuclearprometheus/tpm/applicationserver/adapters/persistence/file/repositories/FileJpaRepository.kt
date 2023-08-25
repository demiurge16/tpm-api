package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.entities.FileDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface FileJpaRepository : JpaRepository<FileDatabaseModel, UUID>, JpaSpecificationExecutor<FileDatabaseModel> {

    fun findAllByProjectId(projectId: UUID): List<FileDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
}
