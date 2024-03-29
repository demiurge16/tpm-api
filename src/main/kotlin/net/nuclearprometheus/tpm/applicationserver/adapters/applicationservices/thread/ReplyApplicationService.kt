package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toDislikeRemoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toLikeRemoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toNewDislike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toNewLike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.UpdateReply
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ReplyDislikeRemoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ReplyLikeRemoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ReplyNewDislike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ReplyNewLike
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Reply as ReplyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ReplyService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ReplyApplicationService(
    private val service: ReplyService,
    private val repository: ReplyRepository,
    private val userContextProvider: UserContextProvider
) {

    private val logger = loggerFor(ReplyApplicationService::class.java)

    fun getReply(replyId: UUID): ReplyResponse {
        logger.info("getReply($replyId)")
        return repository.get(ReplyId(replyId))?.toView() ?: throw NotFoundException("Reply with id $replyId not found")
    }

    fun updateReply(replyId: UUID, request: UpdateReply): ReplyResponse {
        logger.info("updateReply($replyId, $request)")
        return service.update(ReplyId(replyId), request.content).toView()
    }

    fun likeReply(replyId: UUID): ReplyNewLike {
        logger.info("likeReply($replyId)")

        val user = userContextProvider.getCurrentUser()
        return service.like(ReplyId(replyId)).toNewLike(user)
    }

    fun unlikeReply(replyId: UUID): ReplyLikeRemoved {
        logger.info("unlikeReply($replyId)")

        val user = userContextProvider.getCurrentUser()
        return service.unlike(ReplyId(replyId)).toLikeRemoved(user)
    }

    fun dislikeReply(replyId: UUID): ReplyNewDislike {
        logger.info("dislikeReply($replyId)")

        val user = userContextProvider.getCurrentUser()
        return service.dislike(ReplyId(replyId)).toNewDislike(user)
    }

    fun undislikeReply(replyId: UUID): ReplyDislikeRemoved {
        logger.info("undislikeReply($replyId)")

        val user = userContextProvider.getCurrentUser()
        return service.undislike(ReplyId(replyId)).toDislikeRemoved(user)
    }

    fun deleteReply(replyId: UUID) {
        logger.info("deleteReply($replyId)")
        service.delete(ReplyId(replyId))
    }
}