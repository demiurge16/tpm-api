package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.entities.ChatDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ChatJpaRepository : JpaRepository<ChatDatabaseModel, UUID> {
    fun findAllByProjectId(projectId: UUID): List<ChatDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
}
