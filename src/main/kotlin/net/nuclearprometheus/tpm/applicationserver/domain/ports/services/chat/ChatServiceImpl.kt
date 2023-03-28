package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.ProjectService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember.TeamMemberService

class ChatServiceImpl(
    private val chatRepository: ChatRepository,
    private val teamMemberService: TeamMemberService,
    private val projectService: ProjectService,
) : ChatService {
    override fun getAll() = chatRepository.getAll()

    override fun get(id: ChatId) = chatRepository.get(id)

    override fun create(
        name: String,
        description: String,
        projectId: ProjectId,
        owner: TeamMemberId,
        participants: List<TeamMemberId>
    ): Chat {
        projectService.get(projectId) ?: throw NotFoundException("Project does not exist")

        val chat = Chat(
            name = name,
            description = description,
            projectId = projectId,
            owner = teamMemberService.get(owner) ?: throw NotFoundException("Owner does not exist"),
            participants = teamMemberService.get(participants)
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
        chat.transferOwnership(teamMemberService.get(newOwner) ?: throw NotFoundException("New owner does not exist"))

        return chatRepository.update(chat)
    }

    override fun addParticipant(id: ChatId, participant: TeamMemberId): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.addParticipant(teamMemberService.get(participant) ?: throw NotFoundException("Participant does not exist"))

        return chatRepository.update(chat)
    }

    override fun removeParticipant(id: ChatId, participant: TeamMemberId): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.removeParticipant(teamMemberService.get(participant) ?: throw NotFoundException("Participant does not exist"))

        return chatRepository.update(chat)
    }

    override fun addMessage(id: ChatId, message: Message): Chat {
        val chat = chatRepository.get(id) ?: throw NotFoundException("Chat does not exist")
        chat.addMessage(message)

        return chatRepository.update(chat)
    }
}