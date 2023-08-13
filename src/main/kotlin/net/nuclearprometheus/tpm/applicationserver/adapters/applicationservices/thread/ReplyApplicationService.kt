package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toNewLike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.ReplyRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ReplyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ReplyService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ReplyApplicationService(
    private val service: ReplyService,
    private val repository: ReplyRepository,
    private val userContextProvider: UserContextProvider
) {

    private val logger = loggerFor(ReplyApplicationService::class.java)

    fun getReply(replyId: UUID): ReplyResponse.View {
        logger.info("getReply($replyId)")
        return repository.get(ReplyId(replyId))?.toView() ?: throw NotFoundException("Reply with id $replyId not found")
    }

    fun updateReply(replyId: UUID, request: ReplyRequest.Update): ReplyResponse.View {
        logger.info("updateReply($replyId, $request)")
        return service.update(ReplyId(replyId), request.content).toView()
    }

    fun likeReply(replyId: UUID): ReplyResponse.NewLike {
        logger.info("likeReply($replyId)")

        val user = userContextProvider.getCurrentUser()
        return service.like(ReplyId(replyId), user.id).toNewLike()
    }

    fun dislikeReply(replyId: UUID): ReplyResponse.NewLike {
        logger.info("dislikeReply($replyId)")

        val user = userContextProvider.getCurrentUser()
        return service.dislike(ReplyId(replyId), user.id).toNewLike()
    }

    fun deleteReply(replyId: UUID) {
        logger.info("deleteReply($replyId)")
        repository.delete(ReplyId(replyId))
    }
}