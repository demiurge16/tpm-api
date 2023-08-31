package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.ReplyApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.ReplyRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/reply")
class ReplyController(private val service: ReplyApplicationService) {

    private val logger = loggerFor(ReplyController::class.java)

    @GetMapping("/{replyId}")
    fun getReply(@PathVariable(name = "replyId") replyId: UUID) = with(logger) {
        info("GET /api/v1/reply/$replyId")

        ResponseEntity.ok().body(service.getReply(replyId))
    }

    @PutMapping("/{replyId}")
    fun updateReply(@PathVariable(name = "replyId") replyId: UUID, @RequestBody request: ReplyRequest.Update) = with(logger) {
        info("PUT /api/v1/reply/$replyId")

        ResponseEntity.ok().body(service.updateReply(replyId, request))
    }

    @PatchMapping("/{replyId}/like")
    fun likeReply(@PathVariable(name = "replyId") replyId: UUID) = with(logger) {
        info("PATCH /api/v1/reply/$replyId/like")

        ResponseEntity.ok().body(service.likeReply(replyId))
    }

    @PatchMapping("/{replyId}/unlike")
    fun unlikeReply(@PathVariable(name = "replyId") replyId: UUID) = with(logger) {
        info("PATCH /api/v1/reply/$replyId/like")

        ResponseEntity.ok().body(service.unlikeReply(replyId))
    }

    @PatchMapping("/{replyId}/dislike")
    fun dislikeReply(@PathVariable(name = "replyId") replyId: UUID) = with(logger) {
        info("PATCH /api/v1/reply/$replyId/dislike")

        ResponseEntity.ok().body(service.dislikeReply(replyId))
    }

    @PatchMapping("/{replyId}/undislike")
    fun undislikeReply(@PathVariable(name = "replyId") replyId: UUID) = with(logger) {
        info("PATCH /api/v1/reply/$replyId/undislike")

        ResponseEntity.ok().body(service.undislikeReply(replyId))
    }

    @DeleteMapping("/{replyId}")
    fun deleteReply(@PathVariable(name = "replyId") replyId: UUID) = with(logger) {
        info("DELETE /api/v1/reply/$replyId")

        ResponseEntity.ok().body(service.deleteReply(replyId))
    }
}
