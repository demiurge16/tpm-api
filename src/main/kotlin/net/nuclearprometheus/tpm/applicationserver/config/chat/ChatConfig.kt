package net.nuclearprometheus.tpm.applicationserver.config.chat

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat.ChatService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat.ChatServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatConfig(
    private val chatRepository: ChatRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(ChatService::class.java)

    @Bean
    fun chatService(): ChatService = ChatServiceImpl(chatRepository, teamMemberRepository, projectRepository, logger)

}
