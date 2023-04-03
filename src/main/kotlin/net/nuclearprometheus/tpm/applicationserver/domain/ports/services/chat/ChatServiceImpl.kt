package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class ChatServiceImpl(
    private val chatRepository: ChatRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val projectRepository: ProjectRepository,
    private val logger: Logger
) : ChatService {

    override fun create(
        name: String,
        description: String,
        projectId: ProjectId,
        owner: TeamMemberId,
        participants: List<TeamMemberId>
    ): Chat {
        projectRepository.get(projectId) ?: throw NotFoundException("Project does not exist")

        val chat = Chat(
            title = name,
            description = description,
            projectId = projectId,
            owner = teamMemberRepository.get(owner) ?: throw NotFoundException("Owner does not exist"),
            participants = teamMemberRepository.get(participants)
        )

        return chatRepository.create(chat)
    }

    override fun update(id: ChatId, name: String, description: String): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.update(name, description)

        return chatRepository.update(chat)
    }

    override fun activate(id: ChatId): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.activate()

        return chatRepository.update(chat)
    }

    override fun freeze(id: ChatId): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.freeze()

        return chatRepository.update(chat)
    }

    override fun archive(id: ChatId): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.archive()

        return chatRepository.update(chat)
    }

    override fun transferOwnership(id: ChatId, newOwner: TeamMemberId): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.transferOwnership(teamMemberRepository.get(newOwner) ?: throw NotFoundException("New owner does not exist"))

        return chatRepository.update(chat)
    }

    override fun addParticipant(id: ChatId, participant: TeamMemberId): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.addParticipant(teamMemberRepository.get(participant) ?: throw NotFoundException("Participant does not exist"))

        return chatRepository.update(chat)
    }

    override fun removeParticipant(id: ChatId, participant: TeamMemberId): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.removeParticipant(teamMemberRepository.get(participant) ?: throw NotFoundException("Participant does not exist"))

        return chatRepository.update(chat)
    }

    override fun addMessage(id: ChatId, message: Message): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.addMessage(message)

        return chatRepository.update(chat)
    }
}