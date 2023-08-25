package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ReplyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.ReplyRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ReplyResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ReplyService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ThreadReplyApplicationService(
    private val service: ReplyService,
    private val repository: ReplyRepository,
    private val userContextProvider: UserContextProvider
) {

    private val logger = loggerFor(ThreadReplyApplicationService::class.java)

    fun getRepliesForThread(threadId: UUID): Pageable<ReplyResponse.View> {
        logger.info("getRepliesForThread($threadId)")
        return singlePage(
            repository.getAllByThreadId(ThreadId(threadId)).map { it.toView() }
        )
    }

    fun addReplyToThread(id: UUID, request: ReplyRequest.Create): ReplyResponse.View {
        logger.info("addReplyToThread($id, $request)")

        val currentUser = userContextProvider.getCurrentUser()
        return service.create(
            request.content,
            currentUser.id,
            ThreadId(id),
            request.parentReplyId?.let { ReplyId(it) }
        ).toView()
    }
}