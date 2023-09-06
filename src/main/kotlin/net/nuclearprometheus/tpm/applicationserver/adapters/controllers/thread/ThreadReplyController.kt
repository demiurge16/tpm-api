package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.ThreadReplyApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.ReplyRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/thread")
class ThreadReplyController(private val service: ThreadReplyApplicationService) {

    private val logger = loggerFor(ThreadReplyController::class.java)

    @GetMapping("/{threadId}/reply")
    fun getReplies(@PathVariable(name = "threadId") id: UUID) = with(logger) {
        info("GET /api/v1/thread/$id/reply")

        ResponseEntity.ok().body(service.getRepliesForThread(id))
    }

    @PostMapping("/{threadId}/reply")
    fun addReply(@PathVariable(name = "threadId") id: UUID, @RequestBody request: ReplyRequest.Create) = with(logger) {
        info("POST /api/v1/thread/$id/reply")

        ResponseEntity.ok().body(service.addReplyToThread(id, request))
    }
}