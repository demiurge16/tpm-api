package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.entities.MessageDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface MessageJpaRepository : JpaRepository<MessageDatabaseModel, UUID> {

    fun findAllByChatId(chatId: UUID): List<MessageDatabaseModel>
    fun deleteAllByChatId(chatId: UUID)

    @Query("""SELECT m FROM Message m WHERE m.chatId IN (SELECT c.id FROM Chat c WHERE c.projectId = :projectId)""")
    fun deleteAllByProjectId(@Param("projectId") projectId: UUID)
}
