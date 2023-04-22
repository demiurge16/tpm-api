package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses.ChatResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses.Member
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses.Status
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat

object ChatMapper {

    fun Chat.toView() = ChatResponse.Chat(
        id = id.value,
        title = title,
        description = description,
        status = Status(
            status = status,
            name = status.name,
            description = status.description,
        ),
        owner = Member(
            teamMemberId = owner.id.value,
            userId = owner.user.id.value,
            firstName = owner.user.firstName,
            lastName = owner.user.lastName,
            email = owner.user.email,
        ),
        participants = participants.map { participant ->
            Member(
                teamMemberId = participant.id.value,
                userId = participant.user.id.value,
                firstName = participant.user.firstName,
                lastName = participant.user.lastName,
                email = participant.user.email,
            )
        },
        projectId = projectId.value,
    )

    fun Chat.toChatStatus() = ChatResponse.ChatStatus(
        id = id.value,
        status = Status(
            status = status,
            name = status.name,
            description = status.description,
        )
    )

    fun Chat.toNewParticipant() = participants.last().let {
        ChatResponse.ChatMember(
            id = id.value,
            member = Member(
                teamMemberId = it.id.value,
                userId = it.user.id.value,
                firstName = it.user.firstName,
                lastName = it.user.lastName,
                email = it.user.email
            )
        )
    }

    fun Chat.toNewOwner() = ChatResponse.ChatMember(
        id = id.value,
        member = Member(
            teamMemberId = owner.id.value,
            userId = owner.user.id.value,
            firstName = owner.user.firstName,
            lastName = owner.user.lastName,
            email = owner.user.email
        )
    )
}
