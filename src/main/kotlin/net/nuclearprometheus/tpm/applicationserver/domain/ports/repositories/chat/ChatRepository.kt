package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ChatRepository : BaseRepository<Chat, ChatId> {

    fun getAllByProjectId(projectId: ProjectId): List<Chat>
    fun deleteAllByProjectId(projectId: ProjectId)
}
