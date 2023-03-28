package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId

interface ChatService {

    fun getAll(): List<Chat>
    fun get(id: ChatId): Chat?
    fun create(
        name: String,
        description: String,
        projectId: ProjectId,
        owner: TeamMemberId,
        participants: List<TeamMemberId>
    ): Chat

    fun update(
        id: ChatId,
        name: String,
        description: String
    ): Chat

    fun activate(id: ChatId): Chat
    fun freeze(id: ChatId): Chat
    fun archive(id: ChatId): Chat
    fun transferOwnership(id: ChatId, newOwner: TeamMemberId): Chat
    fun addParticipant(id: ChatId, participant: TeamMemberId): Chat
    fun removeParticipant(id: ChatId, participant: TeamMemberId): Chat
    fun addMessage(id: ChatId, message: Message): Chat
}
