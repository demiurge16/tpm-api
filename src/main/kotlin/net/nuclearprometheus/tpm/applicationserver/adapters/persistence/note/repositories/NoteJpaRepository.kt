package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.note.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.note.entities.NoteDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NoteJpaRepository : JpaRepository<NoteDatabaseModel, UUID> {
    fun findAllByProjectId(projectId: UUID): List<NoteDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
}
