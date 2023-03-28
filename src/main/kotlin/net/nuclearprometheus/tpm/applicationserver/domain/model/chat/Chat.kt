package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember

class Chat(
    val id: ChatId = ChatId(),
    name: String,
    description: String,
    status: ChatStatus = ChatStatus.ACTIVE,
    owner: TeamMember,
    val participants: MutableList<TeamMember> = mutableListOf(),
    val messages: MutableList<Message> = mutableListOf()
) {

    var name = name; private set
    var description = description; private set
    var status = status; private set
    var owner = owner; private set

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
        participants.add(participant)
    }

    fun removeParticipant(participant: TeamMember) {
        participants.remove(participant)
    }

    fun addMessage(message: Message) {
        messages.add(message)
    }
}
