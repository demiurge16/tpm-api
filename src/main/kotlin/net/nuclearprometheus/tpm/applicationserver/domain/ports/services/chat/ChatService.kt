package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId

interface ChatService {
    fun create(
        name: String,
        description: String,
        owner: TeamMemberId,
        participants: List<TeamMemberId>
    )

    fun update(
        id: ChatId,
        name: String,
        description: String
    )

    fun activate(id: ChatId)

    fun freeze(id: ChatId)

    fun archive(id: ChatId)

    fun transferOwnership(id: ChatId, newOwner: TeamMemberId)

    fun addParticipant(id: ChatId, participant: TeamMemberId)

    fun removeParticipant(id: ChatId, participant: TeamMemberId)

    fun addMessage(id: ChatId, message: Message)
}
