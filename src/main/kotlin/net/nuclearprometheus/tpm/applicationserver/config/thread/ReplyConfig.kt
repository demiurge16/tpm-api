package net.nuclearprometheus.tpm.applicationserver.config.thread

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ReplyService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ReplyServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReplyConfig(
    private val replyRepository: ReplyRepository,
    private val replyLikeRepository: ReplyLikeRepository,
    private val replyDislikeRepository: ReplyDislikeRepository,
    private val threadRepository: ThreadRepository,
    private val userRepository: UserRepository
) {

    @Bean
    fun replyService(): ReplyService = ReplyServiceImpl(
        repository = replyRepository,
        replyLikeRepository = replyLikeRepository,
        replyDislikeRepository = replyDislikeRepository,
        threadRepository = threadRepository,
        userRepository = userRepository,
        logger = loggerFor(ReplyService::class.java)
    )
}