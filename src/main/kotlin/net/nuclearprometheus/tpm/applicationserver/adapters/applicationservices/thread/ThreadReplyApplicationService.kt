package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.CreateReply
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Reply as ReplyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ReplyService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.singlePage
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ThreadReplyApplicationService(private val service: ReplyService, private val repository: ReplyRepository) {

    private val logger = loggerFor(ThreadReplyApplicationService::class.java)

    fun getRepliesForThread(threadId: UUID): Page<ReplyResponse> {
        logger.info("getRepliesForThread($threadId)")
        return singlePage(
            repository.getAllByThreadId(ThreadId(threadId)).map { it.toView() }
        )
    }

    fun addReplyToThread(id: UUID, request: CreateReply): ReplyResponse {
        logger.info("addReplyToThread($id, $request)")

        return service.create(
            request.content,
            ThreadId(id),
            request.parentReplyId?.let { ReplyId(it) }
        ).toView()
    }
}