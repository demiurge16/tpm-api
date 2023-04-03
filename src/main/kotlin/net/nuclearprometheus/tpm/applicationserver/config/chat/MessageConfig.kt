package net.nuclearprometheus.tpm.applicationserver.config.chat

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.MessageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat.ChatService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat.MessageService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.chat.MessageServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageConfig(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository,
    private val teamMemberRepository: TeamMemberRepository,
) {

    private val logger = loggerFor(ChatService::class.java)

    @Bean
    fun chatService(): MessageService = MessageServiceImpl(messageRepository, chatRepository, teamMemberRepository, logger)

}