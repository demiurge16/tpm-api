package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId

class ChatServiceImpl : ChatService {
    override fun create(name: String, description: String, owner: TeamMemberId, participants: List<TeamMemberId>) {
        TODO("Not yet implemented")
    }

    override fun update(id: ChatId, name: String, description: String) {
        TODO("Not yet implemented")
    }

    override fun activate(id: ChatId) {
        TODO("Not yet implemented")
    }

    override fun freeze(id: ChatId) {
        TODO("Not yet implemented")
    }

    override fun archive(id: ChatId) {
        TODO("Not yet implemented")
    }

    override fun transferOwnership(id: ChatId, newOwner: TeamMemberId) {
        TODO("Not yet implemented")
    }

    override fun addParticipant(id: ChatId, participant: TeamMemberId) {
        TODO("Not yet implemented")
    }

    override fun removeParticipant(id: ChatId, participant: TeamMemberId) {
        TODO("Not yet implemented")
    }

    override fun addMessage(id: ChatId, message: Message) {
        TODO("Not yet implemented")
    }
}