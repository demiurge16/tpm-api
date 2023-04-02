package net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.entities.ChatDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ChatJpaRepository : JpaRepository<ChatDatabaseModel, UUID>
