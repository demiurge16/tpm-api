package net.nuclearprometheus.tpm.applicationserver.adapters.database.file.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.file.entities.FileDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FileJpaRepository : JpaRepository<FileDatabaseModel, UUID> {

    fun findAllByProjectId(projectId: UUID): List<FileDatabaseModel>
}
