package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectChatMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectChatRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat.ChatService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectChatApplicationService(
    private val chatService: ChatService,
    private val chatRepository: ChatRepository
) {

    private val logger = loggerFor(ProjectChatApplicationService::class.java)

    fun getChatsForProject(projectId: UUID) = with(logger) {
        info("getChatsForProject($projectId)")

        singlePage(chatRepository.getAllByProjectId(ProjectId(projectId))).map { it.toView() }
    }

    fun createChat(projectId: UUID, request: ProjectChatRequest.Create) = with(logger) {
        info("createChat($projectId, $request)")

        chatService.create(
            title = request.title,
            description = request.description,
            projectId = ProjectId(projectId),
            owner = TeamMemberId(request.owner),
            participants = request.participants.map { TeamMemberId(it) }
        ).toView()
    }
}