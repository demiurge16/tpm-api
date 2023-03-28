package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember

class Chat(
    val id: ChatId = ChatId(),
    name: String,
    description: String,
    status: ChatStatus = ChatStatus.ACTIVE,
    owner: TeamMember,
    val projectId: ProjectId,
    participants: List<TeamMember> = listOf(),
    messages: List<Message> = listOf()
) {

    var name = name; private set
    var description = description; private set
    var status = status; private set
    var owner = owner; private set
    var participants = participants; private set
    var messages = messages; private set

    fun update(name: String, description: String) {
        this.name = name
        this.description = description
    }

    fun activate() {
        status = ChatStatus.ACTIVE
    }

    fun freeze() {
        status = ChatStatus.FREEZE
    }

    fun archive() {
        status = ChatStatus.ARCHIVE
    }

    fun transferOwnership(newOwner: TeamMember) {
        owner = newOwner
    }

    fun addParticipant(participant: TeamMember) {
        participants = participants.plus(participant)
    }

    fun removeParticipant(participant: TeamMember) {
        participants = participants.minus(participant)
    }

    fun addMessage(message: Message) {
        messages = messages.plus(message)
    }
}
