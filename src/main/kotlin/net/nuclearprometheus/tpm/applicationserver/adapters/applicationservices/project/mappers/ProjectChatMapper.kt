package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectChatResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat

object ProjectChatMapper {

    fun Chat.toView() = ProjectChatResponse.View(
        id = id.value,
        title = title,
        description = description,
        status = ProjectChatResponse.View.ChatStatusView(
            status = status,
            name = status.name,
            description = status.description,
        ),
        owner = ProjectChatResponse.View.ChatMember(
            teamMemberId = owner.id.value,
            userId = owner.user.id.value,
            firstName = owner.user.firstName,
            lastName = owner.user.lastName,
            email = owner.user.email,
        ),
        participants = participants.map { participant ->
            ProjectChatResponse.View.ChatMember(
                teamMemberId = participant.id.value,
                userId = participant.user.id.value,
                firstName = participant.user.firstName,
                lastName = participant.user.lastName,
                email = participant.user.email,
            )
        },
        projectId = projectId.value,
    )
}