package net.nuclearprometheus.tpm.applicationserver.adapters.database.note.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.note.entities.NoteDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NoteJpaRepository : JpaRepository<NoteDatabaseModel, UUID> {
    fun findAllByProjectId(projectId: UUID): List<NoteDatabaseModel>
}
