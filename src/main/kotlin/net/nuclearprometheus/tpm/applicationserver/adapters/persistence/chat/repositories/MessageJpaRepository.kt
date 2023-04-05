package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.entities.MessageDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MessageJpaRepository : JpaRepository<MessageDatabaseModel, UUID> {

    fun findAllByChatId(chatId: UUID): List<MessageDatabaseModel>
}
